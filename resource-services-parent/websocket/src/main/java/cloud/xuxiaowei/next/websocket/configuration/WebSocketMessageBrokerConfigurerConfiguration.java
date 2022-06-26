package cloud.xuxiaowei.next.websocket.configuration;

import cloud.xuxiaowei.next.core.properties.CloudWebSocketProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * WebSocket 配置
 * <p>
 * {@link EnableWebSocketMessageBroker}：注解开启使用 STOMP 协议来传输基于代理（message broker）的消息，
 * 这是控制器支持使用{@link MessageMapping},就像使用{@link RequestMapping}一样。
 *
 * @author xuxiaowei
 * @see <a href=
 * "https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/web.html#websocket-fallback-sockjs-client">SockJsClient</a>
 * @since 0.0.1
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfigurerConfiguration implements WebSocketMessageBrokerConfigurer {

	private CloudWebSocketProperties cloudWebSocketProperties;

	private HandshakeInterceptor handshakeInterceptor;

	private WebSocketHandlerDecoratorFactory webSocketHandlerDecoratorFactory;

	@Autowired
	public void setCloudWebSocketProperties(CloudWebSocketProperties cloudWebSocketProperties) {
		this.cloudWebSocketProperties = cloudWebSocketProperties;
	}

	@Autowired
	public void setHandshakeInterceptor(HandshakeInterceptor handshakeInterceptor) {
		this.handshakeInterceptor = handshakeInterceptor;
	}

	@Autowired
	public void setWebSocketHandlerDecoratorFactory(WebSocketHandlerDecoratorFactory webSocketHandlerDecoratorFactory) {
		this.webSocketHandlerDecoratorFactory = webSocketHandlerDecoratorFactory;
	}

	/**
	 * 配置消息代理选项。
	 * <p>
	 * 配置消息代理（Message Broker）。
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {

		// 启用简单的消息代理并配置一个或多个前缀以过滤以代理为目标的目标（例如，前缀为“/topic”的目标）。
		// 广播式配置一个 /topic 消息代理。
		registry.enableSimpleBroker("/topic", "/queue");

		// 为绑定了 @MessageMapping 注释方法的消息指定“/app”前缀。
		// 配置一个或多个前缀以过滤以应用程序注释方法为目标的目标。
		// 例如，前缀为“/app”的目的地可以通过注释方法处理，而其他目的地可以以消息代理为目标（例如“/topic”，“/queue”）。
		// 处理消息时，将从目标中删除匹配前缀以形成查找路径。 这意味着注释不应包含目标前缀。
		// 没有尾部斜杠的前缀将自动附加一个。
		registry.setApplicationDestinationPrefixes("/app");
	}

	/**
	 * 注册 STOMP 端点，将每个端点映射到特定URL，并（可选）启用和配置 SockJS 后备选项。
	 * <p>
	 * 注册 STOMP 协议的节点（endpoint），并映射指定的URL。
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {

		// 注册一个 STOMP 的 endpoint，并指定使用 SockJS 协议。
		// 注册“/toAll”端点，启用SockJS后备选项，以便在 toAll 不可用时可以使用备用传输。
		// SockJS 客户端将尝试连接到“/toAll”并使用可用的最佳传输（toAll，xhr-streaming，xhr-polling等）。
		// 启用 SockJS 后备选项。
		registry.addEndpoint(cloudWebSocketProperties.getEndpointPaths()).addInterceptors(handshakeInterceptor)
				// 跨域配置，未配置将返回HTTP代码403
				.setAllowedOrigins(cloudWebSocketProperties.getAllowedOrigins()).withSockJS();

	}

	/**
	 * 配置与处理从 WebSocket 客户端接收和发送到 WebSocket 客户端的消息相关的选项。
	 */
	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		registry.addDecoratorFactory(webSocketHandlerDecoratorFactory);
	}

}
