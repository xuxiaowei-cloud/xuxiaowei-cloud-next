package cloud.xuxiaowei.next.gateway.handler;

import cloud.xuxiaowei.next.gateway.filter.web.LogWebFilter;
import cloud.xuxiaowei.next.log.service.ILogService;
import cloud.xuxiaowei.next.utils.*;
import cloud.xuxiaowei.next.utils.exception.CloudRuntimeException;
import cloud.xuxiaowei.next.utils.reactive.RequestUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.URI;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

/**
 * 网关 异常 响应处理 {@link WebExceptionHandler}
 * <p>
 * 优先级：必填<br>
 * 大于 0：不正常<br>
 * 小于等于 0：正常<br>
 *
 * <code>org.springframework.web.reactive.function.client.DefaultClientResponseBuilder#body(String)</code>
 *
 * @author xuxiaowei
 * @see Mono#empty()
 * @see Mono#error(Throwable)
 * @since 0.0.1
 */
@Slf4j
@Component
public class GatewayErrorWebExceptionHandler implements ErrorWebExceptionHandler, Ordered {

	public static final int ORDERED = Ordered.HIGHEST_PRECEDENCE + 10000;

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
	public Mono<Void> handle(@NonNull ServerWebExchange exchange, @NonNull Throwable ex) {

		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();

		String requestId = request.getId();

		Mono<Void> logMono = log(logService, request, response, ex, requestId);
		if (logMono != null) {
			return logMono;
		}

		Response<?> error = Response.error(CodeEnums.S10000.code, CodeEnums.S10000.msg);
		error.setRequestId(requestId);

		if (ex instanceof ResponseStatusException rse) {

			HttpStatusCode statusCode = rse.getStatusCode();
			if (NOT_FOUND.value() == statusCode.value()) {
				error.setCode(CodeEnums.S10001.code);

				URI uri = request.getURI();
				String path = uri.getPath();
				String[] split = path.split("/");
				if (split.length < 1) {
					error.setMsg(CodeEnums.S10001.msg);
				}
				else {
					String service = split[1];
					ServiceEnums serviceEnums = ServiceEnums.getEnum(service);
					if (serviceEnums == null) {
						error.setMsg(CodeEnums.S10001.msg);
					}
					else {
						error.setMsg("未发现：" + serviceEnums.name);
					}
				}
			}
			else if (SERVICE_UNAVAILABLE.value() == statusCode.value()) {
				error.setCode(CodeEnums.S10003.code);

				String msg;
				String message = rse.getMessage();
				if (message == null) {
					msg = CodeEnums.S10003.msg;
				}
				else {
					String service = message.replace("Unable to find instance for ", "");
					ServiceEnums serviceEnums = ServiceEnums.getEnum(service);
					if (serviceEnums != null) {
						msg = CodeEnums.S10003.msg + "：" + serviceEnums.name;
					}
					else {
						msg = CodeEnums.S10003.msg;
					}
					error.setExplain(message);
				}

				error.setMsg(msg);
			}
			else {
				error.setExplain("异常代码待划分");
			}
		}
		else if (ex instanceof ConnectException) {
			error.setCode(CodeEnums.S10002.code);
			error.setMsg(CodeEnums.S10002.msg);
		}
		else if (ex instanceof CloudRuntimeException cloudRuntimeException) {
			error.setCode(cloudRuntimeException.getCode());
			error.setMsg(cloudRuntimeException.getMsg());
			error.setField(cloudRuntimeException.getField());
			error.setExplain(cloudRuntimeException.getExplain());
		}

		return ResponseUtils.writeWith(response, error);
	}

	/**
	 * 日志储存
	 * <p>
	 * 代码抽取
	 * @param logService 日志服务接口
	 * @param request 请求
	 * @param response 响应
	 * @param ex 异常
	 * @param requestId 请求ID
	 * @return 返回日志，不为空时，直接在调用出返回。为空时，继续执行
	 */
	@SuppressWarnings({ "deprecation" })
	public static Mono<Void> log(ILogService logService, ServerHttpRequest request, ServerHttpResponse response,
			Throwable ex, String requestId) {
		HttpHeaders headers = response.getHeaders();

		// 解决服务未发现时跨域问题
		// Access to XMLHttpRequest at '……' from origin '……' has been blocked by CORS
		// policy: No 'Access-Control-Allow-Origin' header is present on the requested
		// resource.
		headers.setAccessControlAllowOrigin(RequestUtils.getOrigin(request));
		// Access to XMLHttpRequest at '……' from origin '……' has been blocked by CORS
		// policy: The value of the 'Access-Control-Allow-Credentials' header in the
		// response is '' which must be 'true' when the request's credentials mode is
		// 'include'. The credentials mode of requests initiated by the XMLHttpRequest is
		// controlled by the withCredentials attribute.
		headers.setAccessControlAllowCredentials(true);

		// 日志中放入请求ID
		MDC.put(Constant.REQUEST_ID, requestId);

		// 请求中放入用户IP
		InetSocketAddress remoteAddress = request.getRemoteAddress();
		if (remoteAddress == null) {
			Response<?> error = Response.error(CodeEnums.X10003.code, CodeEnums.X10003.msg);
			return ResponseUtils.writeWith(response, error);
		}

		// 保存日志
		LogWebFilter.log(logService, remoteAddress, request, requestId, ex);

		MediaType contentType = request.getHeaders().getContentType();

		if (contentType == null) {
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		}
		else if (contentType.equals(MediaType.APPLICATION_JSON)) {
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		}
		else {
			headers.setContentType(contentType);
		}

		headers.setAccept(request.getHeaders().getAccept());
		response.setStatusCode(HttpStatus.OK);

		log.error(requestId, ex);

		return null;
	}

}
