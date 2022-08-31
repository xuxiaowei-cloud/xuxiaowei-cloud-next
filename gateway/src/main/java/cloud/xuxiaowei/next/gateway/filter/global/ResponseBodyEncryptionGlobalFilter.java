package cloud.xuxiaowei.next.gateway.filter.global;

import cloud.xuxiaowei.next.core.properties.CloudAesProperties;
import cloud.xuxiaowei.next.core.properties.CloudSignProperties;
import cloud.xuxiaowei.next.gateway.filter.web.LogWebFilter;
import cloud.xuxiaowei.next.utils.CodeEnums;
import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.Encrypt;
import cloud.xuxiaowei.next.utils.ServiceEnums;
import cloud.xuxiaowei.next.utils.exception.CloudRuntimeException;
import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.symmetric.AES;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * 响应 Body 加密 过滤器
 *
 * @author xuxiaowei
 * @see ServerHttpResponseDecorator
 * @since 0.0.1
 */
@Slf4j
@Component
public class ResponseBodyEncryptionGlobalFilter implements GlobalFilter, Ordered {

	/**
	 * 最低优先级（最大值）：0
	 * <p>
	 * 大于 0 无效
	 */
	public static final int ORDERED = Ordered.HIGHEST_PRECEDENCE + 10000;

	private CloudAesProperties cloudAesProperties;

	private CloudSignProperties cloudSignProperties;

	@Autowired
	public void setCloudAesProperties(CloudAesProperties cloudAesProperties) {
		this.cloudAesProperties = cloudAesProperties;
	}

	@Autowired
	public void setCloudSignProperties(CloudSignProperties cloudSignProperties) {
		this.cloudSignProperties = cloudSignProperties;
	}

	@Setter
	private int order = ORDERED;

	@Override
	public int getOrder() {
		return order;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();

		// 当前访问的路径
		URI uri = request.getURI();
		String path = uri.getPath();

		// 匹配
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		// 匹配：是否为WebSocket服务路径
		boolean matchActuator = antPathMatcher.match(String.format("/%s/**", ServiceEnums.WEBSOCKET.service), path);

		if (matchActuator) {
			// WebSocket 不加密
			return chain.filter(exchange);
		}

		ServerHttpResponseDecorator decorator = new ServerHttpResponseDecorator(response) {

			@NonNull
			@Override
			public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {

				// 这里必须设置，不然以下日志无用户信息
				LogWebFilter.log(request);

				HttpHeaders headers = response.getHeaders();
				MediaType contentType = headers.getContentType();

				if (MediaType.APPLICATION_JSON.includes(contentType)) {
					// 响应数据为JSON，可以加密

					// 默认秘钥
					byte[] keyBytes = cloudAesProperties.getDefaultKey().getBytes();
					// 默认偏移量
					byte[] ivBytes = cloudAesProperties.getDefaultIv().getBytes();

					// 响应中的客户ID
					String clientId = headers.getFirst(OAuth2TokenIntrospectionClaimNames.CLIENT_ID);
					if (StringUtils.hasText(clientId)) {
						// 客户ID存在

						List<CloudAesProperties.Aes> aesList = cloudAesProperties.getList();
						// 遍历客户AES配置
						for (CloudAesProperties.Aes aesProperties : aesList) {
							if (clientId.equals(aesProperties.getClientId())) {
								// 匹配到客户的秘钥配置
								// 使用客户的秘钥配置
								keyBytes = aesProperties.getKey().getBytes();
								ivBytes = aesProperties.getIv().getBytes();
								break;
							}
						}
					}

					// 接口响应中的加密方式（版本）
					String encrypt = headers.getFirst(Constant.ENCRYPT);

					if (StringUtils.hasText(encrypt)) {
						// 存在：响应中的加密方式（版本）

						// 匹配枚举
						Encrypt.AesVersion version = Encrypt.AesVersion.version(encrypt);
						if (version == null) {
							// 未匹配到枚举，使用默认加密方式（版本），即：V1
							return v1(response, keyBytes, ivBytes, body);
						}
						else {
							// @formatter:off
							switch (version) {
								case V1:
									// 加密方式（版本）为 V1 时，使用 V1
									return v1(response, keyBytes, ivBytes, body);
								case V0:
									// 加密方式（版本）为 V0 时，即：不加密，与未匹配时，采用相同的方式
									// 故：此处使用 switch case 的穿透效果
								default:
									// 未匹配到时，使用加密方式（版本）为 V0
									return response.writeWith(body);
							}
							// @formatter:on
						}
					}
					else {
						// 不存在：响应中的加密方式（版本），使用默认加密方式（版本），即：V0，不加密
						return response.writeWith(body);
					}
				}

				// 响应数据不是JSON，不进行加密，直接返回数据
				return response.writeWith(body);
			}

		};

		return chain.filter(exchange.mutate().response(decorator).build());
	}

	/**
	 * 签名
	 * @param response 响应
	 * @param data 数据
	 */
	private void sign(ServerHttpResponse response, byte[] data) {
		// 签名
		Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA, cloudSignProperties.getPrivateKey(), null);
		byte[] signBytes = sign.sign(data);
		String encode = Base64.encode(signBytes);
		response.getHeaders().set(Constant.SIGN, encode);
	}

	/**
	 * 加密方式（版本）V1
	 * @param response 服务器 Http 响应
	 * @param keyBytes 秘钥
	 * @param ivBytes 偏移量
	 * @param body 响应
	 * @return 返回加密后的数据
	 */
	private Mono<Void> v1(ServerHttpResponse response, byte[] keyBytes, byte[] ivBytes,
			Publisher<? extends DataBuffer> body) {

		HttpHeaders headers = response.getHeaders();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		Encrypt.AesVersion aesVersion = Encrypt.AesVersion.V1;

		AES aes = new AES(aesVersion.mode, aesVersion.padding, keyBytes, ivBytes);

		// 设置加密版本
		headers.set(Constant.ENCRYPT, aesVersion.version);
		// 暴露响应头（否则 axios 将无法获取）
		headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, Constant.ENCRYPT);
		headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, Constant.SIGN);

		@SuppressWarnings("unchecked")
		Flux<? extends DataBuffer> fluxDataBuffer = (Flux<? extends DataBuffer>) body;

		return response.writeWith(fluxDataBuffer.buffer().map(dataBuffer -> {

			DataBuffer join = response.bufferFactory().join(dataBuffer);

			byte[] bytes = new byte[join.readableByteCount()];
			join.read(bytes);
			DataBufferUtils.release(join);

			String originalText = new String(bytes);

			log.debug("加密前 body：{}", originalText);

			String encryptBase64 = aes.encryptBase64(originalText);

			log.debug("加密后 body：{}", encryptBase64);

			Encrypt encrypt = new Encrypt();
			encrypt.setCiphertext(encryptBase64);

			byte[] responseBytes;
			try {
				String value = objectMapper.writeValueAsString(encrypt);

				log.debug("返回 body：{}", value);

				// 加密后的响应，设置响应内容的长度
				response.getHeaders().setContentLength(value.length());

				responseBytes = value.getBytes();
			}
			catch (JsonProcessingException e) {
				log.error("body 加密后组装的对象 Encrypt 转 JSON String 失败", e);
				throw new CloudRuntimeException(CodeEnums.ERROR.code, "body 加密后组装的对象 Encrypt 转 JSON String 失败", null,
						e.getMessage());
			}

			// 签名
			sign(response, responseBytes);

			return response.bufferFactory().wrap(responseBytes);
		}));
	}

}
