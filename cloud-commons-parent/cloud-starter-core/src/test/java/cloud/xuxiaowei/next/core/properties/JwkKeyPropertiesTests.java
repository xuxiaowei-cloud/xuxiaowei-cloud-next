package cloud.xuxiaowei.next.core.properties;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

/**
 * JWK 配置 测试类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
class JwkKeyPropertiesTests {

	/**
	 * 生成 RSA 密钥对
	 */
	@Test
	void rsa() {
		KeyPair keyPair = SecureUtil.generateKeyPair(AsymmetricAlgorithm.RSA_ECB_PKCS1.getValue(), 2048);
		RSA rsa = new RSA(keyPair.getPrivate(), keyPair.getPublic());

		String privateKeyBase64 = rsa.getPrivateKeyBase64();
		String publicKeyBase64 = rsa.getPublicKeyBase64();
		log.info("privateKeyBase64：{}", publicKeyBase64);
		log.info("privateKeyBase64：{}", privateKeyBase64);
	}

}
