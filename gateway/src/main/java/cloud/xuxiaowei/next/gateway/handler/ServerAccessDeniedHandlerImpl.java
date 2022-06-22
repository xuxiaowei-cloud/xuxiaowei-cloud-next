package cloud.xuxiaowei.next.gateway.handler;

import cloud.xuxiaowei.next.utils.CodeEnums;
import cloud.xuxiaowei.next.utils.Response;
import cloud.xuxiaowei.next.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 服务器访问被拒绝处理程序
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Component
public class ServerAccessDeniedHandlerImpl implements ServerAccessDeniedHandler {

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {

		ServerHttpResponse response = exchange.getResponse();

		log.error("服务器访问被拒绝处理程序", denied);

		Response<?> error = Response.error(CodeEnums.T00000.code, CodeEnums.T00000.msg);
		error.setExplain(denied.getMessage());
		return ResponseUtils.writeWith(response, error);
	}

}
