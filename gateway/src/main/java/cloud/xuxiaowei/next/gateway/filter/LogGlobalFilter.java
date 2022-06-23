package cloud.xuxiaowei.next.gateway.filter;

import cloud.xuxiaowei.next.log.service.ILogService;
import cloud.xuxiaowei.next.utils.CodeEnums;
import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.Response;
import cloud.xuxiaowei.next.utils.ResponseUtils;
import cloud.xuxiaowei.next.utils.reactive.RequestUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;

/**
 * 日志 过滤器
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Component
public class LogGlobalFilter implements GlobalFilter, Ordered {

	/**
	 * 最低优先级（最大值）：0
	 * <p>
	 * 大于 0 无效
	 */
	public static final int ORDERED = Ordered.HIGHEST_PRECEDENCE;

	private ILogService logService;

	@Autowired
	public void setLogService(ILogService logService) {
		this.logService = logService;
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

		// 日志中放入请求ID
		String requestId = request.getId();
		MDC.put(Constant.REQUEST_ID, requestId);

		// 请求中放入用户IP
		InetSocketAddress remoteAddress = request.getRemoteAddress();
		if (remoteAddress == null) {
			Response<?> error = Response.error(CodeEnums.X10003.code, CodeEnums.X10003.msg);
			return ResponseUtils.writeWith(response, error);
		}

		// 保存日志
		log(logService, remoteAddress, request, requestId, null);

		return chain.filter(exchange);
	}

	public static void log(ILogService logService, InetSocketAddress remoteAddress, ServerHttpRequest request,
			String requestId, Throwable ex) {

		InetAddress address = remoteAddress.getAddress();
		String hostAddress = address.getHostAddress();
		MDC.put(Constant.IP, hostAddress);

		String method = request.getMethod().name();
		URI uri = request.getURI();
		String requestUri = uri.getPath();
		String queryString = uri.getQuery();
		String headersMap = RequestUtils.getHeadersJson(request);
		String userAgent = RequestUtils.getUserAgent(request);

		logService.saveLog(hostAddress, requestId, null, method, requestUri, queryString, headersMap, userAgent, ex);

	}

}
