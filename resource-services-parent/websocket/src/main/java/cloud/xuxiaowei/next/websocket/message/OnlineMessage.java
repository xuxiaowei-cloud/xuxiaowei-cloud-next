package cloud.xuxiaowei.next.websocket.message;

import lombok.Data;

/**
 * 在线消息
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class OnlineMessage {

	/**
	 * 消息类型
	 */
	private String type = "online";

	/**
	 * 是否上线
	 */
	private boolean online;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 在线用户
	 */
	private int number;

}
