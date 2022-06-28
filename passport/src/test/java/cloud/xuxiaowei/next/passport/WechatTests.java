package cloud.xuxiaowei.next.passport;

import cn.hutool.crypto.asymmetric.RSA;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.client.RestTemplate;

import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信 授权 测试类
 *
 * @author xuxiaowei
 * @see 0.0.1
 */
@Slf4j
class WechatTests {

	@Test
	void token() {

		String appid = "wxcf4f3a217a8bc728";
		String code = "093EQXkl21tZq94dm9ml2BaAsd2EQXku";
		String clientId = "xuxiaowei_client_wechat_applet_id";
		String clientSecret = "xuxiaowei_client_wechat_applet_secret";

		Map<String, String> map = new HashMap<>();
		map.put("grant_type", "wechat_applet");
		map.put("client_id", clientId);
		map.put("client_secret", clientSecret);
		map.put("appid", appid);
		map.put("code", code);

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

		String url = "http://127.0.0.1:1401/oauth2/token?grant_type={grant_type}&client_id={client_id}&client_secret={client_secret}&appid={appid}&code={code}";
		String postForObject = restTemplate.postForObject(url, httpEntity, String.class, map);

		log.info("授权信息：" + postForObject);

	}

	/**
	 * // @formatter:off
	 * {
	 *   "tokenValue" : "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJvNUdCTzVmWlA0MzdKaEo3NGw0MFhTWmQ1RWI4IiwidW5pb25pZCI6Im9UZlVKeE95SHRhV3gxVl9hN3ZyRXB2NmQ5Q1UiLCJzZXNzaW9uS2V5IjoiUUhGMXdBcGtRd3FlZUI4elFaWDFLQT09Iiwib3BlbmlkIjoibzVHQk81ZlpQNDM3SmhKNzRsNDBYU1pkNUViOCIsImlzcyI6Imh0dHA6XC9cLzEyNy4wLjAuMToxNDAxIiwiYXV0aG9yaXRpZXMiOlsid2VjaGF0X2FwcGxldCIsInNuc2FwaV9iYXNlIiwiYWFhYWEiXSwiYXVkIjoieHV4aWFvd2VpX2NsaWVudF93ZWNoYXRfYXBwbGV0X2lkIiwibmJmIjoxNjU2MzkzNTI3LCJzY29wZSI6WyJzbnNhcGlfYmFzZSIsImFhYWFhIl0sImFwcGlkIjoid3hjZjRmM2EyMTdhOGJjNzI4IiwiZXhwIjoxNjU2NDM2NzI3LCJpYXQiOjE2NTYzOTM1MjcsInVzZXJuYW1lIjoibzVHQk81ZlpQNDM3SmhKNzRsNDBYU1pkNUViOCJ9.rjXAA-wuDC1HWYkT-Zh5bmZxPGhJb8t-jYhudlovDG7B2neRhnbCszHLR23vyB_hN8QCPtzi3vC1mH8b9F9tIoj9xxJoG_CYzEvsJ8uug4kRFGuQUHfen9csvqgbEzMXh__0xgd1CUkbqMBQEcQfOFiEJDf_QITQF2KDB_Iwis1qe-I2jpKIyZQgav-tz9sLjnx93oaE4hH-WQc27FOmxmr3Be1jODZ1gAubDd0RkI23RzKXM27EiNtNqzwLasc8cOxCyA8goH9kK991qVbiYkREgefQJeeuUEHmDKG_3RUskO0FHlgCrgGzEI_DQHiNWz8kS1n2cnlr4jpQinYnSA",
	 *   "issuedAt" : 1656393527.000000000,
	 *   "expiresAt" : 1656436727.000000000,
	 *   "headers" : {
	 *     "alg" : "RS256"
	 *   },
	 *   "claims" : {
	 *     "sub" : "o5GBO5fZP437JhJ74l40XSZd5Eb8",
	 *     "unionid" : "oTfUJxOyHtaWx1V_a7vrEpv6d9CU",
	 *     "sessionKey" : "QHF1wApkQwqeeB8zQZX1KA==",
	 *     "openid" : "o5GBO5fZP437JhJ74l40XSZd5Eb8",
	 *     "iss" : "http://127.0.0.1:1401",
	 *     "authorities" : [ "wechat_applet", "snsapi_base", "aaaaa" ],
	 *     "aud" : [ "xuxiaowei_client_wechat_applet_id" ],
	 *     "nbf" : 1656393527.000000000,
	 *     "scope" : [ "snsapi_base", "aaaaa" ],
	 *     "appid" : "wxcf4f3a217a8bc728",
	 *     "exp" : 1656436727.000000000,
	 *     "iat" : 1656393527.000000000,
	 *     "username" : "o5GBO5fZP437JhJ74l40XSZd5Eb8"
	 *   },
	 *   "id" : null,
	 *   "subject" : "o5GBO5fZP437JhJ74l40XSZd5Eb8",
	 *   "notBefore" : 1656393527.000000000,
	 *   "issuer" : "http://127.0.0.1:1401",
	 *   "audience" : [ "xuxiaowei_client_wechat_applet_id" ]
	 * }
	 * // @formatter:on
	 */
	@Test
	void decode() throws JsonProcessingException {
		RSA rsa = new RSA(null,
				"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvUEZbnpoYCSVveN8/h3ouADkX1l5/qpS/vaVuWMOMpVbWqiVysrL7x8NV0i0NKBf+Ufa0aJBMYtsRGTsKOGv4ulwoUCMDizZ47xCUVMR6JCbm3qVejeK5GWPatrRFPWXwIL5G4nk4ZkpEhFUn0qeJwEPG70QNxZDNJVIqbDK0CROZifd/7REy9SQjIsQbVjUmC2J09IXE4FK3YDIltUOJLf7ASnkIb5al/IelKLIkoYaiI4Jjw6/zK2QXwNaO74FSOIbxDM/yixrkuArtsrqLbxjw/BMlW5pLguTfbeXobbmS+t1MgIfCmWye5GXCsuuS+K/RElLmvBtT+o1xZNBowIDAQAB");

		NimbusJwtDecoder.PublicKeyJwtDecoderBuilder publicKeyJwtDecoderBuilder = NimbusJwtDecoder
				.withPublicKey((RSAPublicKey) rsa.getPublicKey());
		NimbusJwtDecoder nimbusJwtDecoder = publicKeyJwtDecoderBuilder.build();

		Jwt jwt = nimbusJwtDecoder.decode(
				"eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJvNUdCTzVmWlA0MzdKaEo3NGw0MFhTWmQ1RWI4IiwidW5pb25pZCI6Im9UZlVKeE95SHRhV3gxVl9hN3ZyRXB2NmQ5Q1UiLCJzZXNzaW9uS2V5IjoiUUhGMXdBcGtRd3FlZUI4elFaWDFLQT09Iiwib3BlbmlkIjoibzVHQk81ZlpQNDM3SmhKNzRsNDBYU1pkNUViOCIsImlzcyI6Imh0dHA6XC9cLzEyNy4wLjAuMToxNDAxIiwiYXV0aG9yaXRpZXMiOlsid2VjaGF0X2FwcGxldCIsInNuc2FwaV9iYXNlIiwiYWFhYWEiXSwiYXVkIjoieHV4aWFvd2VpX2NsaWVudF93ZWNoYXRfYXBwbGV0X2lkIiwibmJmIjoxNjU2MzkzNTI3LCJzY29wZSI6WyJzbnNhcGlfYmFzZSIsImFhYWFhIl0sImFwcGlkIjoid3hjZjRmM2EyMTdhOGJjNzI4IiwiZXhwIjoxNjU2NDM2NzI3LCJpYXQiOjE2NTYzOTM1MjcsInVzZXJuYW1lIjoibzVHQk81ZlpQNDM3SmhKNzRsNDBYU1pkNUViOCJ9.rjXAA-wuDC1HWYkT-Zh5bmZxPGhJb8t-jYhudlovDG7B2neRhnbCszHLR23vyB_hN8QCPtzi3vC1mH8b9F9tIoj9xxJoG_CYzEvsJ8uug4kRFGuQUHfen9csvqgbEzMXh__0xgd1CUkbqMBQEcQfOFiEJDf_QITQF2KDB_Iwis1qe-I2jpKIyZQgav-tz9sLjnx93oaE4hH-WQc27FOmxmr3Be1jODZ1gAubDd0RkI23RzKXM27EiNtNqzwLasc8cOxCyA8goH9kK991qVbiYkREgefQJeeuUEHmDKG_3RUskO0FHlgCrgGzEI_DQHiNWz8kS1n2cnlr4jpQinYnSA");

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
		String json = objectWriter.writeValueAsString(jwt);
		log.info("\n" + json);
	}

}
