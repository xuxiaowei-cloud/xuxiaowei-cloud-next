package cloud.xuxiaowei.websocket.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * 用于 WebSocket 握手请求的拦截器。 可用于检查握手请求和响应，以及将属性传递给目标{@link WebSocketHandler}。
 * <p>
 * 可将不同的 STOMP 的 endpoint 指定不同的{@link HandshakeInterceptor}
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Configuration
public class HandshakeInterceptorConfiguration implements HandshakeInterceptor {

	/**
	 * @param attributes
	 * 用于在{@link WebSocketHandler}中的{@link WebSocketSession#getAttributes()}中获取
	 */
	@Override
	public boolean beforeHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
			@NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();

		log.debug("用户：" + name + " 建立连接");

		return true;
	}

	@Override
	public void afterHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
			@NonNull WebSocketHandler wsHandler, Exception exception) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();

		log.info("用户：" + name + " 握手后");

	}

}
