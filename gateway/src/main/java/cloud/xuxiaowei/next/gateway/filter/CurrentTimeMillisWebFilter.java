package cloud.xuxiaowei.next.gateway.filter;

import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.Response;
import cloud.xuxiaowei.next.utils.ResponseUtils;
import cloud.xuxiaowei.next.utils.map.ResponseMap;
import lombok.Setter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 时间戳 过滤器
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Component
public class CurrentTimeMillisWebFilter implements WebFilter, Ordered {

	/**
	 * 时间戳
	 */
	public static final String CURRENT_TIME_MILLIS = "/currentTimeMillis";

	/**
	 * 最低优先级（最大值）：0
	 * <p>
	 * 大于 0 无效
	 */
	public static final int ORDERED = Ordered.HIGHEST_PRECEDENCE + 1040000;

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
		RequestPath path = request.getPath();
		String value = path.value();

		if (CURRENT_TIME_MILLIS.equals(value)) {
			ServerHttpResponse response = exchange.getResponse();
			HttpHeaders headers = response.getHeaders();

			Response<?> ok = ResponseMap.ok().put(Constant.CURRENT_TIME_MILLIS, System.currentTimeMillis());

			headers.setAccessControlAllowOrigin(request.getHeaders().getOrigin());
			headers.setAccessControlAllowCredentials(true);

			return ResponseUtils.writeWith(response, ok);
		}

		return chain.filter(exchange);
	}

}
