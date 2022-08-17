package cloud.xuxiaowei.next.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 微服务 AES 配置
 * <p>
 * 运行时刷新
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties("cloud.aes")
public class CloudAesProperties {

	/**
	 * 默认秘钥
	 */
	private String defaultKey;

	/**
	 * 默认偏移量
	 */
	private String defaultIv;

	/**
	 * 检查加密数据中的时间戳是否在有效时间范围内，单位：秒
	 */
	private long time = 300;

	/**
	 * 强制加密路径
	 */
	private List<ServicePath> forcePaths = new ArrayList<>();

	/**
	 * AES 配置列表
	 */
	private List<Aes> list = new ArrayList<>();

	/**
	 * AES 配置
	 *
	 * @author xuxiaowei
	 * @since 0.0.1
	 */
	@Data
	public static class Aes {

		/**
		 * 客户ID
		 */
		private String clientId;

		/**
		 * 秘钥
		 */
		private String key;

		/**
		 * 偏移量
		 */
		private String iv;

	}

	/**
	 * 服务路径
	 *
	 * @author xuxiaowei
	 * @since 0.0.1
	 */
	@Data
	public static class ServicePath {

		/**
		 * 服务名
		 */
		private String service;

		/**
		 * 路径
		 */
		private List<String> paths;

	}

}
