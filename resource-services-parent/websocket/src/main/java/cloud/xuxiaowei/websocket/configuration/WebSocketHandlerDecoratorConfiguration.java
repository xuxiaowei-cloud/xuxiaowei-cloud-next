package cloud.xuxiaowei.websocket.configuration;

import cloud.xuxiaowei.next.utils.SecurityUtils;
import cloud.xuxiaowei.websocket.message.OnlineMessage;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket 消息和生命周期事件的处理程序。
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
public class WebSocketHandlerDecoratorConfiguration extends WebSocketHandlerDecorator {

	/**
	 * 总在线用户
	 */
	private static final Map<String, WebSocketSession> ALL_WEB_SOCKET_SESSION = new ConcurrentHashMap<>();

	private SimpMessagingTemplate messagingTemplate;

	public void setApplicationContext(ApplicationContext applicationContext) {
		messagingTemplate = applicationContext.getBean(SimpMessagingTemplate.class);

		log.info("使用 ApplicationContext 获取 SimpMessagingTemplate：{}", messagingTemplate);
	}

	public WebSocketHandlerDecoratorConfiguration(WebSocketHandler delegate) {
		super(delegate);
	}

	/**
	 * 在WebSocket协商成功并且WebSocket连接打开并准备好使用后调用。
	 * @param session 发送WebSocket消息：{@link TextMessage}或{@link BinaryMessage}。
	 * <p>
	 * <strong>注意：</strong>底层标准WebSocket会话（JSR-356）不允许并发发送。 因此必须同步发送。
	 * 为了确保这一点，一个选项是使用{@link ConcurrentWebSocketSessionDecorator}包装{@link WebSocketSession}。
	 * @throws Exception 此方法可以处理或传播异常; 有关详细信息，请参阅类级Javadoc。
	 */
	@Override
	public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);

		// 客户端与服务器端建立连接后，此处记录谁上线了
		Principal principal = session.getPrincipal();
		String userName = SecurityUtils.getUserName(principal);
		log.info("上线: " + userName);

		if (userName != null) {
			ALL_WEB_SOCKET_SESSION.put(userName, session);
		}

		// 用户上线通知
		// 放在添加用户后面
		online(userName, true);

	}

	/**
	 * 在新的WebSocket消息到达时调用。
	 * @throws Exception 此方法可以处理或传播异常; 有关详细信息，请参阅类级Javadoc。
	 */
	@Override
	public void handleMessage(@NonNull WebSocketSession session, @NonNull WebSocketMessage<?> message)
			throws Exception {
		super.handleMessage(session, message);

		// 需要解析授权Token
		Principal principal = session.getPrincipal();
		String userName = SecurityUtils.getUserName(principal);

		log.info("接收到用户: " + userName + " 的消息");
		log.info("消息内容：\n" + message.getPayload());

	}

	/**
	 * 处理底层WebSocket消息传输中的错误。
	 * @throws Exception 此方法可以处理或传播异常; 有关详细信息，请参阅类级Javadoc。
	 */
	@Override
	public void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) throws Exception {
		super.handleTransportError(session, exception);

		// 需要解析授权Token
		Principal principal = session.getPrincipal();
		String userName = SecurityUtils.getUserName(principal);

		log.info("接收到用户: " + userName + " 的异常");
		log.info("异常信息：" + exception.getMessage());

	}

	/**
	 * WebSocket连接被任何一方关闭后，或者在发生传输错误后调用。 虽然会话在技术上可能仍然是开放的，但取决于底层实现，此时不鼓励发送消息，并且很可能不会成功。
	 * @throws Exception 此方法可以处理或传播异常; 有关详细信息，请参阅类级Javadoc。
	 */
	@Override
	public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus closeStatus)
			throws Exception {
		super.afterConnectionClosed(session, closeStatus);

		// 客户端与服务器端断开连接后，此处记录谁下线了
		// 需要解析授权Token
		Principal principal = session.getPrincipal();
		String userName = SecurityUtils.getUserName(principal);

		log.info("离线: " + userName);

		if (userName != null) {
			ALL_WEB_SOCKET_SESSION.remove(userName);
		}

		// 用户下线通知
		// 放在移除用户后面
		online(userName, false);

	}

	/**
	 * 用户上线/下线通知
	 * <p>
	 * 放在添加用户后面
	 * @param username 上线用户名
	 * @param online true 上线，false 下线
	 */
	private void online(String username, boolean online) {

		OnlineMessage onlineMessage = new OnlineMessage();
		onlineMessage.setOnline(online);
		onlineMessage.setUsername(username);
		onlineMessage.setNumber(ALL_WEB_SOCKET_SESSION.size());

		String payload = JSONObject.toJSONString(onlineMessage);

		for (Map.Entry<String, WebSocketSession> entry : ALL_WEB_SOCKET_SESSION.entrySet()) {
			String key = entry.getKey();
			if (!username.equals(key)) {
				WebSocketSession value = entry.getValue();

				messagingTemplate.convertAndSend("/topic/broadcast", payload,
						Collections.singletonMap(SimpMessageHeaderAccessor.SESSION_ID_HEADER, value.getId()));

			}
		}
	}

}
