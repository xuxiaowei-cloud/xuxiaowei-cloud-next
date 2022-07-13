package cloud.xuxiaowei.next.canal.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Canal 配置
 * <p>
 * 运行时刷新
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties("cloud.canal")
public class CloudCanalProperties {

	private String hostname;

	private int port = 11111;

	private String destination = "example";

	private String username = "";

	private String password = "";

	private int batchSize = 1000;

}
