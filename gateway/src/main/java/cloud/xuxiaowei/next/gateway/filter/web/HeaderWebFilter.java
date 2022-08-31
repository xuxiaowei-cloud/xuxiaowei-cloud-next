package cloud.xuxiaowei.next.gateway.filter.web;

import cloud.xuxiaowei.next.utils.Constant;
import lombok.Setter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 请求头 Header 过滤器
 * <p>
 * 将请求ID传递给服务
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Component
public class HeaderWebFilter implements WebFilter, Ordered {

	/**
	 * 最低优先级（最大值）：0
	 * <p>
	 * 大于 0 无效
	 */
	public static final int ORDERED = Ordered.HIGHEST_PRECEDENCE + 40000;

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

		String requestId = request.getId();

		LinkedHashMap<String, String> headersMap = new LinkedHashMap<>();
		headersMap.put(Constant.REQUEST_ID, requestId);

		Consumer<HttpHeaders> httpHeaders = httpHeader -> {
			for (Map.Entry<String, String> entry : headersMap.entrySet()) {
				httpHeader.set(entry.getKey(), entry.getValue());
			}
		};

		ServerHttpRequest newRequest = exchange.getRequest().mutate().headers(httpHeaders).build();
		ServerWebExchange build = exchange.mutate().request(newRequest).build();

		return chain.filter(build);
	}

}
