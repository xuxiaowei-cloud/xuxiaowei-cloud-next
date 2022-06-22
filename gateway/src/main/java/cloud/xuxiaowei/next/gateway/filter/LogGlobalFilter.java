package cloud.xuxiaowei.next.gateway.filter;

import cloud.xuxiaowei.next.log.entity.Log;
import cloud.xuxiaowei.next.log.service.ILogService;
import cloud.xuxiaowei.next.utils.*;
import cloud.xuxiaowei.next.utils.exception.ExceptionUtils;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

		String requestId = request.getId();
		MDC.put(Constant.REQUEST_ID, requestId);

		InetSocketAddress remoteAddress = request.getRemoteAddress();
		if (remoteAddress == null) {
			Response<?> error = Response.error(CodeEnums.X10003.code, CodeEnums.X10003.msg);
			return ResponseUtils.writeWith(response, error);
		}

		InetAddress address = remoteAddress.getAddress();
		String hostName = address.getHostName();
		String hostAddress = address.getHostAddress();
		MDC.put(Constant.IP, hostAddress);

		save(logService, request, hostAddress, hostName, null);

		return chain.filter(exchange);
	}

	/**
	 * 根据请求保存数据
	 * @param logService 日志服务
	 * @param request 请求
	 * @param hostAddress 用户IP
	 * @param hostName 用户访问域名
	 * @param ex 异常
	 */
	public static void save(ILogService logService, ServerHttpRequest request, String hostAddress, String hostName,
			Throwable ex) {

		LocalDate localDate = LocalDate.now();
		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		int day = localDate.getDayOfMonth();

		LocalTime localTime = LocalTime.now();
		int hour = localTime.getHour();

		String method = request.getMethod().name();
		String requestId = request.getId();

		String headersMap = RequestUtils.getHeadersJson(request);
		String userAgent = RequestUtils.getUserAgent(request);

		URI uri = request.getURI();
		String requestUri = uri.getPath();
		String queryString = uri.getQuery();

		Log log = new Log();
		log.setModule(ServiceEnums.GATEWAY.service);
		log.setDate(localDate);
		log.setYear(year);
		log.setMonth(month);
		log.setDay(day);
		log.setHour(hour);
		log.setMethod(method);
		log.setRequestUri(requestUri);
		log.setQueryString(queryString);
		log.setHeadersMap(headersMap);
		log.setUserAgent(userAgent);
		log.setRequestId(requestId);
		log.setSessionId(null);
		log.setException(ex == null ? null : ExceptionUtils.getStackTrace(ex));
		log.setCreateUsername("该字段待确认");
		log.setCreateIp(hostAddress);
		log.setCreateHostName(hostName);
		log.setCreateDate(LocalDateTime.now());

		logService.save(log);
	}

}
