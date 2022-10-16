package cloud.xuxiaowei.next.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 微服务 记住我 配置
 * <p>
 * 运行时刷新
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties("cloud.remember-me")
public class CloudRememberMeProperties {

	/**
	 * 秘钥
	 */
	private String key = "xuxiaowei.com.cn";

	/**
	 * 记住我域名
	 */
	private String rememberMeCookieDomain = "example.next.xuxiaowei.cloud";

	/**
	 * 记住我参数名
	 */
	private String rememberMeParameter = "remember-me";

	/**
	 * 记住我 Cookie 名
	 */
	private String rememberMeCookieName = "remember-me";

	/**
	 * 记住我 Token 有效时间
	 * <p>
	 * 2592000 = 60 * 60 * 24 * 30
	 */
	private int tokenValiditySeconds = 60 * 60 * 24 * 30;

}
