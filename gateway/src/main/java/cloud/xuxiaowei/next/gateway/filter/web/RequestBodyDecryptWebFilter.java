package cloud.xuxiaowei.next.gateway.filter.web;

import cloud.xuxiaowei.next.core.properties.CloudAesProperties;
import cloud.xuxiaowei.next.utils.CodeEnums;
import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.Encrypt;
import cloud.xuxiaowei.next.utils.exception.CloudRuntimeException;
import cn.hutool.crypto.symmetric.AES;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames.CLIENT_ID;

/**
 * 请求体 Body 解密 过滤器
 *
 * @author xuxiaowei
 * @see ServerHttpRequestDecorator
 * @see <a href=
 * "https://stackoverflow.com/questions/66822340/spring-webflux-security-and-request-body">spring-webflux-security-and-request-body</a>
 * @since 0.0.1
 */
@Slf4j
@Component
public class RequestBodyDecryptWebFilter implements WebFilter, Ordered {

	/**
	 * 最低优先级（最大值）：0
	 * <p>
	 * 大于 0 无效
	 */
	public static final int ORDERED = Ordered.HIGHEST_PRECEDENCE + 90000;

	/**
	 * 时间戳长度
	 */
	private static final int CURRENT_TIME_MILLIS_LENGTH = (System.currentTimeMillis() + "").length();

	private CloudAesProperties cloudAesProperties;

	@Autowired
	public void setCloudAesProperties(CloudAesProperties cloudAesProperties) {
		this.cloudAesProperties = cloudAesProperties;
	}

	@Setter
	private int order = ORDERED;

	@Override
	public int getOrder() {
		return order;
	}

	@NonNull
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {

		ServerHttpRequest request = exchange.getRequest();

		MediaType contentType = request.getHeaders().getContentType();

		if (MediaType.APPLICATION_JSON.includes(contentType)) {
			// 请求数据为JSON，可以解密

			byte[] decrypt = decrypt(exchange);

			if (decrypt == null) {
				// 解密内容为 null 时，不处理
				return chain.filter(exchange);
			}

			// 创建一个新的请求
			ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(request) {
				@NonNull
				@Override
				public Flux<DataBuffer> getBody() {
					// 修改请求头
					return Flux.just(exchange.getResponse().bufferFactory().wrap(decrypt));
				}

				@NonNull
				@Override
				public HttpHeaders getHeaders() {
					HttpHeaders headers = new HttpHeaders();
					headers.addAll(super.getHeaders());
					// 修改请求体长度
					headers.setContentLength(decrypt.length);
					return headers;
				}

			};

			// 使用新的请求继续执行
			return chain.filter(exchange.mutate().request(decorator).build());
		}
		else {
			// 请求数据不是JSON，不进行解密，直接返回数据
			return chain.filter(exchange);
		}
	}

	private byte[] decrypt(ServerWebExchange exchange) {

		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();

		HttpHeaders headers = request.getHeaders();

		// 请求体
		byte[] bytes = exchange.getAttribute(RequestBodyDecryptBeforeWebFilter.BODY_DECRYPT_BYTES);

		if (bytes == null) {
			// 请求体 null 时，不处理
			return null;
		}

		// 解密后的数据
		byte[] decrypt;

		// 默认秘钥
		byte[] keyBytes = cloudAesProperties.getDefaultKey().getBytes();
		// 默认偏移量
		byte[] ivBytes = cloudAesProperties.getDefaultIv().getBytes();

		// 接口请求中的加密方式（版本）
		String encrypt = headers.getFirst(Constant.ENCRYPT);
		if (StringUtils.hasText(encrypt)) {
			// 存在：请求中的加密方式（版本）

			// 响应中的客户ID
			String clientId = headers.getFirst(CLIENT_ID);
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

			// 匹配枚举
			Encrypt.AesVersion version = Encrypt.AesVersion.version(encrypt);

			if (version == null) {
				// 未匹配到枚举，请求体不处理，使用原始请求体
				decrypt = bytes;
			}
			else {
				switch (version) {
					case V1:
						decrypt = v1(response, bytes, clientId, keyBytes, ivBytes);
						break;
					case V0:
						// 加密方式（版本）为 V0 时，使用 V0，与未匹配时，采用相同的方式
						// 故：此处使用 switch case 的穿透效果
					default:
						decrypt = bytes;
				}
			}

		}
		else {
			// 接口请求体强制解密检查
			decrypt = force(request, response, bytes, keyBytes, ivBytes);
		}

		return decrypt;
	}

	/**
	 * 解密方式（版本）V1
	 * @param response 服务器 Http 响应
	 * @param bytes 请求
	 * @param clientId 客户ID
	 * @param keyBytes 秘钥
	 * @param ivBytes 偏移量
	 * @return 返回解密后的数据
	 */
	private byte[] v1(ServerHttpResponse response, byte[] bytes, String clientId, byte[] keyBytes, byte[] ivBytes) {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		Encrypt.AesVersion aesVersion = Encrypt.AesVersion.V1;

		AES aes = new AES(aesVersion.mode, aesVersion.padding, keyBytes, ivBytes);
		// 设置解密方式（版本）
		response.getHeaders().set(Constant.DECRYPT, aesVersion.version);
		// 设置解密秘钥、偏移量（如果为 default，代表使用默认秘钥、偏移量）
		response.getHeaders()
			.set(OAuth2TokenIntrospectionClaimNames.CLIENT_ID, clientId == null ? Constant.DEFAULT : clientId);

		log.debug("解密前 body：{}", new String(bytes));

		Encrypt encrypt;
		try {
			encrypt = objectMapper.readValue(bytes, Encrypt.class);
		}
		catch (JsonMappingException e) {
			log.error("body 转对象 Encrypt 时 Json 映射异常", e);
			throw new CloudRuntimeException(CodeEnums.ERROR.code, "body 转对象 Encrypt 时 Json 映射异常", null, e.getMessage());
		}
		catch (IOException e) {
			log.error("body 转对象 Encrypt 时 IO 异常", e);
			throw new CloudRuntimeException(CodeEnums.ERROR.code, "body 转对象 Encrypt 时 IO 异常", null, e.getMessage());
		}

		String ciphertext = encrypt.getCiphertext();

		if (StringUtils.hasText(ciphertext)) {
			byte[] decrypt;
			try {
				decrypt = aes.decrypt(ciphertext);
			}
			catch (Exception e) {
				throw new CloudRuntimeException(CodeEnums.ERROR.code, "解密失败", Encrypt.CIPHERTEXT, e.getMessage());
			}

			String decryptStr = new String(decrypt);

			// 检查 时间戳
			String content = checkCurrentTimeMillis(decryptStr);

			log.debug("解密后 body：{}", decryptStr);
			log.debug("向后传递的 body：{}", content);

			return content.getBytes();
		}
		else {
			throw new CloudRuntimeException(CodeEnums.ERROR.code, "解密密文不能为空", Encrypt.CIPHERTEXT);
		}
	}

	/**
	 * 强制检查
	 * @param request 服务器 Http 请求
	 * @param response 服务器 Http 响应
	 * @param bytes 请求
	 * @param keyBytes 秘钥
	 * @param ivBytes 偏移量
	 * @return 返回 强制检查结果
	 */
	private byte[] force(ServerHttpRequest request, ServerHttpResponse response, byte[] bytes, byte[] keyBytes,
			byte[] ivBytes) {
		URI uri = request.getURI();
		String path = uri.getPath();

		// 该路径是否强制加密
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		List<CloudAesProperties.ServicePath> forcePaths = cloudAesProperties.getForcePaths();
		for (CloudAesProperties.ServicePath servicePath : forcePaths) {

			// 服务名
			String service = servicePath.getService();
			// 路径
			List<String> paths = servicePath.getPaths();
			for (String p : paths) {
				// 服务名与路径拼接
				String pattern = service.startsWith("/") ? service : "/" + service;
				pattern = p.startsWith("/") ? pattern + p : pattern + "/" + p;

				// 匹配
				boolean match = antPathMatcher.match(pattern, path);
				if (match) {
					// 匹配到，需要强制加密，但未找到加密方式（版本），使用默认加密方式（版本）、秘钥、偏移量进行解密
					return v1(response, bytes, null, keyBytes, ivBytes);
				}
			}
		}

		return bytes;
	}

	/**
	 * 检查 时间戳
	 */
	private String checkCurrentTimeMillis(String decryptStr) {
		if (StringUtils.hasText(decryptStr)) {
			if (decryptStr.length() < CURRENT_TIME_MILLIS_LENGTH) {
				throw new CloudRuntimeException(CodeEnums.ERROR.code, "解密字符串时间戳长度不合法", Constant.CURRENT_TIME_MILLIS);
			}

			String substring = decryptStr.substring(0, CURRENT_TIME_MILLIS_LENGTH);
			long currentTimeMillis;
			try {
				currentTimeMillis = Long.parseLong(substring);
			}
			catch (Exception e) {
				log.error("解密字符串时间戳类型不合法", e);
				throw new CloudRuntimeException(CodeEnums.ERROR.code, "解密字符串时间戳类型不合法", Constant.CURRENT_TIME_MILLIS);
			}

			long time = Math.abs(System.currentTimeMillis() - currentTimeMillis);

			// noinspection AlibabaUndefineMagicConstant
			if (time > cloudAesProperties.getTime() * 1000) {
				throw new CloudRuntimeException(CodeEnums.ERROR.code, "解密字符串时间戳不合法", Constant.CURRENT_TIME_MILLIS);
			}

		}
		else {
			throw new CloudRuntimeException(CodeEnums.ERROR.code, "解密字符串不能为空", Constant.CURRENT_TIME_MILLIS);
		}

		return decryptStr.substring(CURRENT_TIME_MILLIS_LENGTH);
	}

}
