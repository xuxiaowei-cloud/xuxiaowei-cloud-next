package cloud.xuxiaowei.next.gateway.filter.web;

import cloud.xuxiaowei.next.log.service.ILogService;
import cloud.xuxiaowei.next.utils.*;
import cloud.xuxiaowei.next.utils.reactive.RequestUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Map;

/**
 * 日志 过滤器
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Component
public class LogWebFilter implements WebFilter, Ordered {

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

	@NonNull
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {

		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();

		log(request);

		// 请求中放入用户IP
		InetSocketAddress remoteAddress = request.getRemoteAddress();
		if (remoteAddress == null) {
			Response<?> error = Response.error(CodeEnums.X10003.code, CodeEnums.X10003.msg);
			return ResponseUtils.writeWith(response, error);
		}

		String requestId = request.getId();

		// 保存日志
		log(logService, remoteAddress, request, requestId, null);

		return chain.filter(exchange);
	}

	public static void log(ILogService logService, InetSocketAddress remoteAddress, ServerHttpRequest request,
			String requestId, Throwable ex) {

		InetAddress address = remoteAddress.getAddress();
		String hostAddress = address.getHostAddress();
		HttpMethod httpMethod = request.getMethod();
		String method = httpMethod.name();
		URI uri = request.getURI();
		String requestUri = uri.getPath();
		String queryString = uri.getQuery();
		String headersMap = RequestUtils.getHeadersJson(request);
		String authorization = RequestUtils.getAuthorization(request);
		String userAgent = RequestUtils.getUserAgent(request);

		logService.saveLog(hostAddress, requestId, null, method, requestUri, queryString, headersMap, authorization,
				userAgent, ex);

	}

	public static void log(ServerHttpRequest request) {

		// 日志中放入请求ID
		String requestId = request.getId();
		MDC.put(Constant.REQUEST_ID, requestId);

		InetSocketAddress remoteAddress = request.getRemoteAddress();
		if (remoteAddress != null) {
			InetAddress address = remoteAddress.getAddress();
			String hostAddress = address.getHostAddress();
			MDC.put(Constant.IP, hostAddress);
		}

		String authorization = RequestUtils.getAuthorization(request);
		Map<String, String> payloadMap = SecurityUtils.getPayloadStringMap(authorization);
		String usersId = payloadMap.get(Constant.USERS_ID);
		MDC.put(Constant.USERS_ID, usersId);

		String username = payloadMap.get(Constant.USERNAME);
		String sub = payloadMap.get(JwtClaimNames.SUB);
		if (StringUtils.hasText(username)) {
			MDC.put(Constant.NAME, username);
		}
		else if (StringUtils.hasText(sub)) {
			MDC.put(Constant.NAME, sub);
		}
	}

}
