package cloud.xuxiaowei.next.gateway.filter.global;

import cloud.xuxiaowei.next.core.properties.CloudWebSocketProperties;
import cloud.xuxiaowei.next.utils.ServiceEnums;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 响应体 {@link HttpHeaders} 过滤器
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Component
public class ResponseHeadersGlobalFilter implements GlobalFilter, Ordered {

	public static final int ORDERED = Ordered.HIGHEST_PRECEDENCE;

	private CloudWebSocketProperties cloudWebSocketProperties;

	@Autowired
	public void setCloudWebSocketProperties(CloudWebSocketProperties cloudWebSocketProperties) {
		this.cloudWebSocketProperties = cloudWebSocketProperties;
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
		URI uri = request.getURI();
		String path = uri.getPath();

		String[] endpointPaths = cloudWebSocketProperties.getEndpointPaths();
		if (endpointPaths != null) {
			for (String endpointPath : endpointPaths) {
				if (String.format("/%s%s/info", ServiceEnums.WEBSOCKET.service, endpointPath).equals(path)) {

					ServerHttpResponseDecorator decorator = new ServerHttpResponseDecorator(exchange.getResponse());
					HttpHeaders headers = decorator.getHeaders();

					// WebSocket 响应时，移除
					// Access-Control-Allow-Origin、Access-Control-Allow-Credentials
					headers.remove(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN);
					headers.remove(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS);

					return chain.filter(exchange.mutate().response(decorator).build());
				}
			}
		}

		return chain.filter(exchange);
	}

}
