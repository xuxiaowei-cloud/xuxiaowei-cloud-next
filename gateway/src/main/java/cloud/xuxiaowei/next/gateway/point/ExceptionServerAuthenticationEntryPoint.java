package cloud.xuxiaowei.next.gateway.point;

import cloud.xuxiaowei.next.gateway.handler.GatewayErrorWebExceptionHandler;
import cloud.xuxiaowei.next.log.service.ILogService;
import cloud.xuxiaowei.next.utils.CodeEnums;
import cloud.xuxiaowei.next.utils.Response;
import cloud.xuxiaowei.next.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.web.server.BearerTokenServerAuthenticationEntryPoint;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 异常服务器认证入口点
 *
 * @author xuxiaowei
 * @see BearerTokenServerAuthenticationEntryPoint
 * @since 0.0.1
 */
@Slf4j
@Component
public class ExceptionServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

	private ILogService logService;

	@Autowired
	public void setLogService(ILogService logService) {
		this.logService = logService;
	}

	@Override
	public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {

		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();

		String requestId = request.getId();

		Mono<Void> logHandler = GatewayErrorWebExceptionHandler.log(logService, request, response, ex, requestId);
		if (logHandler != null) {
			return logHandler;
		}

		Response<?> error = Response.error(CodeEnums.T10001.code, CodeEnums.T10001.msg);
		error.setRequestId(requestId);

		if (ex instanceof AuthenticationCredentialsNotFoundException) {
			error.setCode(CodeEnums.T10003.code);
			error.setMsg(CodeEnums.T10003.msg);
		}
		else if (ex instanceof InvalidBearerTokenException invalidBearerTokenException) {
			OAuth2Error oauth2Error = invalidBearerTokenException.getError();

			if (oauth2Error instanceof BearerTokenError bearerTokenError) {

				error.setCode(CodeEnums.T10002.code);
				error.setMsg(CodeEnums.T10002.msg);
				error.setExplain(bearerTokenError.getDescription());
			}
			else {
				error.setCode(CodeEnums.T10004.code);
				error.setMsg(CodeEnums.T10004.msg);
			}

		}
		else {
			error.setExplain("异常代码待划分");
		}

		return ResponseUtils.writeWith(response, error);
	}

}
