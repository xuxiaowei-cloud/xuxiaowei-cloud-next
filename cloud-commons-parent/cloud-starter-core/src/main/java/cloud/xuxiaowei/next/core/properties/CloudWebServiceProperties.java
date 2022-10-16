package cloud.xuxiaowei.next.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 微服务 WebService 配置
 * <p>
 * 运行时刷新
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties("cloud.web-service")
public class CloudWebServiceProperties {

	/**
	 * 命名空间
	 * <p>
	 * 用于无权限时返回数据格式
	 */
	private List<String> namespaceUriList = Collections.emptyList();

}
