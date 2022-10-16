package cloud.xuxiaowei.next.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 微服务 黑名单 配置
 * <p>
 * 运行时刷新
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties("cloud.black-list")
public class CloudBlackListProperties {

	/**
	 * 黑名单IP
	 */
	private List<String> ipList = Collections.emptyList();

	/**
	 * 黑名单IP与服务
	 */
	private List<BlackList> services = Collections.emptyList();

	/**
	 * 黑名单
	 *
	 * @author xuxiaowei
	 * @since 0.0.1
	 */
	@Data
	public static class BlackList {

		/**
		 * IP
		 */
		private String ip;

		/**
		 * 服务名
		 */
		private String name;

		/**
		 * 路径
		 * <p>
		 * 拦截所有：/**
		 */
		private List<String> pathList = Collections.singletonList("/**");

	}

}
