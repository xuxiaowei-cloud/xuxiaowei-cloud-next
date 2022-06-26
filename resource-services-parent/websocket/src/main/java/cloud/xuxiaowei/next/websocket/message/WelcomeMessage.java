package cloud.xuxiaowei.next.websocket.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 广播消息
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WelcomeMessage {

	/**
	 * 消息发送者
	 */
	private String from;

	/**
	 * 消息内容
	 */
	private String msg;

}
