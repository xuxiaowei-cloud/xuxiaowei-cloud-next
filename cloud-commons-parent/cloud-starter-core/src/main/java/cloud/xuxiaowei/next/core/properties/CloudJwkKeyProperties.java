package cloud.xuxiaowei.next.core.properties;

import cn.hutool.crypto.asymmetric.RSA;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

/**
 * 微服务 JWK 配置
 * <p>
 * RSA 非对称性加密，必须为 2048 位
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@Component
@ConfigurationProperties("cloud.jwk")
public class CloudJwkKeyProperties {

	/**
	 * RSA 秘钥对
	 */
	private Rsa rsa;

	/**
	 * RSA 秘钥对
	 * <p>
	 * RSA 非对称性加密，必须为 2048 位
	 *
	 * @author xuxiaowei
	 * @since 0.0.1
	 */
	@Data
	public static class Rsa {

		/**
		 * RSA 公钥
		 */
		private String publicKey;

		/**
		 * RSA 私钥
		 */
		private String privateKey;

	}

	/**
	 * 获取 RSA 公钥 对象
	 * @return 返回 RSA 公钥
	 */
	public PublicKey publicKey() {
		return new RSA(null, rsa.publicKey).getPublicKey();
	}

	/**
	 * 获取 RSA 公钥 对象
	 * @return 返回 RSA 公钥
	 */
	public RSAPublicKey rsaPublicKey() {
		return (RSAPublicKey) publicKey();
	}

	/**
	 * 获取 RSA 私钥 对象
	 * @return 返回 RSA 私钥
	 */
	public PrivateKey privateKey() {
		return new RSA(rsa.privateKey, null).getPrivateKey();
	}

}
