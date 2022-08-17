package cloud.xuxiaowei.next.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微服务 签名 配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@Component
@ConfigurationProperties("cloud.sign")
public class CloudSignProperties {

	/**
	 * 私钥，用于签名
	 */
	private String privateKey;

	/**
	 * 公钥，用于验证签名，分发给客户端
	 */
	private String publicKey;

}
