package cloud.xuxiaowei.next.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 微服务 WebSocket 配置
 * <p>
 * 运行时刷新
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties("cloud.web-socket")
public class CloudWebSocketProperties {

	/**
	 * STOMP over WebSocket 端点
	 */
	private String[] endpointPaths;

	/**
	 * 允许域
	 */
	private String[] allowedOrigins = new String[] {};

}
