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
 * 微信小程序 授权 测试类
 *
 * @author xuxiaowei
 * @see 0.0.1
 */
@Slf4j
class OAuth2WeChatMiniProgramTests {

	@Test
	void token() {

		String appid = "wxcf4f3a217a8bc728";
		String code = "073sz5ll2RUyr94GOJml2aGU2u1sz5lR";
		String clientId = "xuxiaowei_client_wechat_applet_id";
		String clientSecret = "xuxiaowei_client_wechat_applet_secret";

		Map<String, String> map = new HashMap<>();
		map.put("grant_type", "wechat_miniprogram");
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
	 *   "tokenValue" : "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJvNUdCTzVmWlA0MzdKaEo3NGw0MFhTWmQ1RWI4IiwidW5pb25pZCI6Im9UZlVKeE95SHRhV3gxVl9hN3ZyRXB2NmQ5Q1UiLCJzZXNzaW9uS2V5IjoiQ3pFMFllQm1oVWZOOG8yaFVcL1pHVlE9PSIsIm9wZW5pZCI6Im81R0JPNWZaUDQzN0poSjc0bDQwWFNaZDVFYjgiLCJpc3MiOiJodHRwOlwvXC8xMjcuMC4wLjE6MTQwMSIsImF1dGhvcml0aWVzIjpbIndlY2hhdF9taW5pcHJvZ3JhbSIsInNuc2FwaV9iYXNlIiwic25zYXBpX2luZm8iXSwiYXVkIjoieHV4aWFvd2VpX2NsaWVudF93ZWNoYXRfYXBwbGV0X2lkIiwibmJmIjoxNjU2NTE1NTExLCJzY29wZSI6WyJzbnNhcGlfYmFzZSIsInNuc2FwaV9pbmZvIl0sImFwcGlkIjoid3hjZjRmM2EyMTdhOGJjNzI4IiwiZXhwIjoxNjU2NTU4NzExLCJpYXQiOjE2NTY1MTU1MTEsInVzZXJuYW1lIjoibzVHQk81ZlpQNDM3SmhKNzRsNDBYU1pkNUViOCJ9.t3oFElLzn0HljDYQhTv--U7mvdGudbsU88IoN4pVUn3wTQ8M29kIuaBlBo6RN4blofLbWPyj5mXHZBRuHMO-KE2QrCtckmsQbIPKHRO-dAy6i6BZhJnJ8OVJfi0HlIBDi0jijLubWutjLVF3pr6GmVhkOzGWfDi9nNjlnRxd6Qx1ecgznuUGFGcSv8Ty3Zu4JeVfdZ-3_JW8zqk6UyL0oxdhgXF7wQkErqn5VaYZZm1S421yOBZpsXQ68N1ur5i4bi93HDw_9SiawPGd7ozbVhTqwekGMIOsjU42pPH38TWzenmTjpmJqC6nuXlr4vcRKGYPa-6IyWfHhReYD98eIg",
	 *   "issuedAt" : 1656515511.000000000,
	 *   "expiresAt" : 1656558711.000000000,
	 *   "headers" : {
	 *     "alg" : "RS256"
	 *   },
	 *   "claims" : {
	 *     "sub" : "o5GBO5fZP437JhJ74l40XSZd5Eb8",
	 *     "unionid" : "oTfUJxOyHtaWx1V_a7vrEpv6d9CU",
	 *     "sessionKey" : "CzE0YeBmhUfN8o2hU/ZGVQ==",
	 *     "openid" : "o5GBO5fZP437JhJ74l40XSZd5Eb8",
	 *     "iss" : "http://127.0.0.1:1401",
	 *     "authorities" : [ "wechat_miniprogram", "snsapi_base", "snsapi_info" ],
	 *     "aud" : [ "xuxiaowei_client_wechat_applet_id" ],
	 *     "nbf" : 1656515511.000000000,
	 *     "scope" : [ "snsapi_base", "snsapi_info" ],
	 *     "appid" : "wxcf4f3a217a8bc728",
	 *     "exp" : 1656558711.000000000,
	 *     "iat" : 1656515511.000000000,
	 *     "username" : "o5GBO5fZP437JhJ74l40XSZd5Eb8"
	 *   },
	 *   "id" : null,
	 *   "notBefore" : 1656515511.000000000,
	 *   "issuer" : "http://127.0.0.1:1401",
	 *   "subject" : "o5GBO5fZP437JhJ74l40XSZd5Eb8",
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
				"eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJvNUdCTzVmWlA0MzdKaEo3NGw0MFhTWmQ1RWI4IiwidW5pb25pZCI6Im9UZlVKeE95SHRhV3gxVl9hN3ZyRXB2NmQ5Q1UiLCJzZXNzaW9uS2V5IjoiQ3pFMFllQm1oVWZOOG8yaFVcL1pHVlE9PSIsIm9wZW5pZCI6Im81R0JPNWZaUDQzN0poSjc0bDQwWFNaZDVFYjgiLCJpc3MiOiJodHRwOlwvXC8xMjcuMC4wLjE6MTQwMSIsImF1dGhvcml0aWVzIjpbIndlY2hhdF9taW5pcHJvZ3JhbSIsInNuc2FwaV9iYXNlIiwic25zYXBpX2luZm8iXSwiYXVkIjoieHV4aWFvd2VpX2NsaWVudF93ZWNoYXRfYXBwbGV0X2lkIiwibmJmIjoxNjU2NTE1NTExLCJzY29wZSI6WyJzbnNhcGlfYmFzZSIsInNuc2FwaV9pbmZvIl0sImFwcGlkIjoid3hjZjRmM2EyMTdhOGJjNzI4IiwiZXhwIjoxNjU2NTU4NzExLCJpYXQiOjE2NTY1MTU1MTEsInVzZXJuYW1lIjoibzVHQk81ZlpQNDM3SmhKNzRsNDBYU1pkNUViOCJ9.t3oFElLzn0HljDYQhTv--U7mvdGudbsU88IoN4pVUn3wTQ8M29kIuaBlBo6RN4blofLbWPyj5mXHZBRuHMO-KE2QrCtckmsQbIPKHRO-dAy6i6BZhJnJ8OVJfi0HlIBDi0jijLubWutjLVF3pr6GmVhkOzGWfDi9nNjlnRxd6Qx1ecgznuUGFGcSv8Ty3Zu4JeVfdZ-3_JW8zqk6UyL0oxdhgXF7wQkErqn5VaYZZm1S421yOBZpsXQ68N1ur5i4bi93HDw_9SiawPGd7ozbVhTqwekGMIOsjU42pPH38TWzenmTjpmJqC6nuXlr4vcRKGYPa-6IyWfHhReYD98eIg");

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
		String json = objectWriter.writeValueAsString(jwt);
		log.info("\n" + json);
	}

}
