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
		String code = "043By2100okt7O1577200vJEsx1By21J";
		String clientId = "xuxiaowei_client_webchat_miniprogram_id";
		String clientSecret = "xuxiaowei_client_webchat_miniprogram_secret";

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
	 *   "tokenValue" : "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJvNUdCTzVmWlA0MzdKaEo3NGw0MFhTWmQ1RWI4IiwidW5pb25pZCI6Im9UZlVKeE95SHRhV3gxVl9hN3ZyRXB2NmQ5Q1UiLCJvcGVuaWQiOiJvNUdCTzVmWlA0MzdKaEo3NGw0MFhTWmQ1RWI4IiwiaXNzIjoiaHR0cDpcL1wvREVTS1RPUC1ER1FMMkZUOjE0MDEiLCJhdXRob3JpdGllcyI6WyJ3ZWNoYXRfbWluaXByb2dyYW0iLCJzbnNhcGlfYmFzZSJdLCJhdWQiOiJ4dXhpYW93ZWlfY2xpZW50X3dlYmNoYXRfbWluaXByb2dyYW1faWQiLCJuYmYiOjE2NTY1ODczMTAsInNjb3BlIjpbInNuc2FwaV9iYXNlIl0sImFwcGlkIjoid3hjZjRmM2EyMTdhOGJjNzI4IiwiZXhwIjoxNjU2NjMwNTEwLCJpYXQiOjE2NTY1ODczMTAsInVzZXJuYW1lIjoibzVHQk81ZlpQNDM3SmhKNzRsNDBYU1pkNUViOCJ9.cc9rZMo4yHY3d4Gm7K8NUJUxgxnT5ppUZqPAneY54EjJ_xTgr5ONKsXUvmvteuHOvOWB1b-1nelm_d0g-K-9SaV73m130rlesd5H_RlXg1a4q84G2RDOcEPBhW01Qu4Q5FfvjjUXxI95dpl9Dp-3gMl7-mOUWAqCPRS-usD_bdmm6GJhrWRCO82-_vYyxF0W8EdyS33hYwHtdcPiAiF5tl3N2YqGdiG1MfSGr8aTz3nSE1KZkPM3w-mfMqjexM9cSdF0oBE4iu4cuXRhCm_iX8WKtE-URyRiTMgHFQ-76m0ScYl7SqYALZhjrrOF00NIAZbN9w5VEWx3n3KwZeQMMg",
	 *   "issuedAt" : 1656587310.000000000,
	 *   "expiresAt" : 1656630510.000000000,
	 *   "headers" : {
	 *     "alg" : "RS256"
	 *   },
	 *   "claims" : {
	 *     "sub" : "o5GBO5fZP437JhJ74l40XSZd5Eb8",
	 *     "unionid" : "oTfUJxOyHtaWx1V_a7vrEpv6d9CU",
	 *     "openid" : "o5GBO5fZP437JhJ74l40XSZd5Eb8",
	 *     "iss" : "http://DESKTOP-DGQL2FT:1401",
	 *     "authorities" : [ "wechat_miniprogram", "snsapi_base" ],
	 *     "aud" : [ "xuxiaowei_client_webchat_miniprogram_id" ],
	 *     "nbf" : 1656587310.000000000,
	 *     "scope" : [ "snsapi_base" ],
	 *     "appid" : "wxcf4f3a217a8bc728",
	 *     "exp" : 1656630510.000000000,
	 *     "iat" : 1656587310.000000000,
	 *     "username" : "o5GBO5fZP437JhJ74l40XSZd5Eb8"
	 *   },
	 *   "id" : null,
	 *   "subject" : "o5GBO5fZP437JhJ74l40XSZd5Eb8",
	 *   "notBefore" : 1656587310.000000000,
	 *   "issuer" : "http://DESKTOP-DGQL2FT:1401",
	 *   "audience" : [ "xuxiaowei_client_webchat_miniprogram_id" ]
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
				"eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJvNUdCTzVmWlA0MzdKaEo3NGw0MFhTWmQ1RWI4IiwidW5pb25pZCI6Im9UZlVKeE95SHRhV3gxVl9hN3ZyRXB2NmQ5Q1UiLCJvcGVuaWQiOiJvNUdCTzVmWlA0MzdKaEo3NGw0MFhTWmQ1RWI4IiwiaXNzIjoiaHR0cDpcL1wvREVTS1RPUC1ER1FMMkZUOjE0MDEiLCJhdXRob3JpdGllcyI6WyJ3ZWNoYXRfbWluaXByb2dyYW0iLCJzbnNhcGlfYmFzZSJdLCJhdWQiOiJ4dXhpYW93ZWlfY2xpZW50X3dlYmNoYXRfbWluaXByb2dyYW1faWQiLCJuYmYiOjE2NTY1ODczMTAsInNjb3BlIjpbInNuc2FwaV9iYXNlIl0sImFwcGlkIjoid3hjZjRmM2EyMTdhOGJjNzI4IiwiZXhwIjoxNjU2NjMwNTEwLCJpYXQiOjE2NTY1ODczMTAsInVzZXJuYW1lIjoibzVHQk81ZlpQNDM3SmhKNzRsNDBYU1pkNUViOCJ9.cc9rZMo4yHY3d4Gm7K8NUJUxgxnT5ppUZqPAneY54EjJ_xTgr5ONKsXUvmvteuHOvOWB1b-1nelm_d0g-K-9SaV73m130rlesd5H_RlXg1a4q84G2RDOcEPBhW01Qu4Q5FfvjjUXxI95dpl9Dp-3gMl7-mOUWAqCPRS-usD_bdmm6GJhrWRCO82-_vYyxF0W8EdyS33hYwHtdcPiAiF5tl3N2YqGdiG1MfSGr8aTz3nSE1KZkPM3w-mfMqjexM9cSdF0oBE4iu4cuXRhCm_iX8WKtE-URyRiTMgHFQ-76m0ScYl7SqYALZhjrrOF00NIAZbN9w5VEWx3n3KwZeQMMg");

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
		String json = objectWriter.writeValueAsString(jwt);
		log.info("\n" + json);
	}

}
