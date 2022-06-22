package cloud.xuxiaowei.next.gateway.filter;

import cloud.xuxiaowei.next.core.properties.CloudCorsProperties;
import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.reactive.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.DefaultCorsProcessor;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * 在 CORS 之前执行
 *
 * @author xuxiaowei
 * @see DefaultCorsProcessor#process(CorsConfiguration, ServerWebExchange)
 * @since 0.0.1
 */
@Component
public class CorsBeforeWebFilter implements WebFilter {

	private CloudCorsProperties cloudCorsProperties;

	@Autowired
	public void setCloudCorsProperties(CloudCorsProperties cloudCorsProperties) {
		this.cloudCorsProperties = cloudCorsProperties;
	}

	@NonNull
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {

		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();

		URI uri = request.getURI();
		String path = uri.getPath();

		String origin = RequestUtils.getOrigin(request);

		if (origin == null || Constant.NULL.equals(origin)) {
			List<String> allowOrginNullList = cloudCorsProperties.getAllowOrginNullList();
			if (allowOrginNullList != null) {
				// 解决 form action 提交数据无 origin 跨域问题
				if (allowOrginNullList.contains(path)) {
					String schemeHost = RequestUtils.getSchemeHost(request);
					response.getHeaders().setAccessControlAllowOrigin(schemeHost);
				}
			}
		}
		else {
			// 允许跨域时携带授权信息
			response.getHeaders().addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
					request.getHeaders().getAccessControlRequestHeaders());
		}

		return chain.filter(exchange);
	}

}
