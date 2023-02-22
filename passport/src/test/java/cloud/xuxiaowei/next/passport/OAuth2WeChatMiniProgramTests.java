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
		String clientId = "xuxiaowei_client_wechat_miniprogram_id";
		String clientSecret = "xuxiaowei_client_wechat_miniprogram_secret";

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
	 *   "tokenValue" : "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJvNUdCTzVmWlA0MzdKaEo3NGw0MFhTWmQ1RWI4IiwidW5pb25pZCI6Im9UZlVKeE95SHRhV3gxVl9hN3ZyRXB2NmQ5Q1UiLCJvcGVuaWQiOiJvNUdCTzVmWlA0MzdKaEo3NGw0MFhTWmQ1RWI4IiwiaXNzIjoiaHR0cDpcL1wvbG9jYWxob3N0OjE0MDEiLCJhdXRob3JpdGllcyI6WyJ3ZWNoYXRfbWluaXByb2dyYW0iLCJzbnNhcGlfYmFzZSIsInNuc2FwaV9pbmZvIl0sImF1ZCI6Inh1eGlhb3dlaV9jbGllbnRfd2VjaGF0X21pbmlwcm9ncmFtX2lkIiwibmJmIjoxNjU3MjA3NjM0LCJzY29wZSI6WyJzbnNhcGlfYmFzZSIsInNuc2FwaV9pbmZvIl0sImFwcGlkIjoid3hjZjRmM2EyMTdhOGJjNzI4IiwiZXhwIjoxNjU3MjUwODM0LCJpYXQiOjE2NTcyMDc2MzQsInVzZXJuYW1lIjoibzVHQk81ZlpQNDM3SmhKNzRsNDBYU1pkNUViOCJ9.p51ICvOJXgQ60zPrbqkit7VXTcHSf_3RpORJCpzu4miKds0VPSjFGRTkmTEdtPMc9gvIUKU0JuG-wo7nq8yIotSzj0WEEfkMq44UsJEmMgBjLcfoNAkemrbSLXRScaMfJgcvUe9fy2D_KlwxwtxNRL6zlyuKjFwX9Gug8s7bjuKU86haoEmj_P2h2GKEm_p9YiFvruVFD15sKY7ng7uNzOlmBw-Ca-KaQcKGpH6jxMIskI_EN4WpO03MOcTJtuXS2Kz9-Vi5UmvqhsgQbtK966UJsLn86BHZnp0Pxjs6L1zYWEHM5SbHDEqdDI2QBOSINDII0eag8lcBejRXazD-ew",
	 *   "issuedAt" : 1657207634.000000000,
	 *   "expiresAt" : 1657250834.000000000,
	 *   "headers" : {
	 *     "alg" : "RS256"
	 *   },
	 *   "claims" : {
	 *     "sub" : "o5GBO5fZP437JhJ74l40XSZd5Eb8",
	 *     "unionid" : "oTfUJxOyHtaWx1V_a7vrEpv6d9CU",
	 *     "openid" : "o5GBO5fZP437JhJ74l40XSZd5Eb8",
	 *     "iss" : "http://DESKTOP-DGQL2FT:1401",
	 *     "authorities" : [ "wechat_miniprogram", "snsapi_base", "snsapi_info" ],
	 *     "aud" : [ "xuxiaowei_client_wechat_miniprogram_id" ],
	 *     "nbf" : 1657207634.000000000,
	 *     "scope" : [ "snsapi_base", "snsapi_info" ],
	 *     "appid" : "wxcf4f3a217a8bc728",
	 *     "exp" : 1657250834.000000000,
	 *     "iat" : 1657207634.000000000,
	 *     "username" : "o5GBO5fZP437JhJ74l40XSZd5Eb8"
	 *   },
	 *   "id" : null,
	 *   "subject" : "o5GBO5fZP437JhJ74l40XSZd5Eb8",
	 *   "notBefore" : 1657207634.000000000,
	 *   "issuer" : "http://DESKTOP-DGQL2FT:1401",
	 *   "audience" : [ "xuxiaowei_client_wechat_miniprogram_id" ]
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
				"eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJvNUdCTzVmWlA0MzdKaEo3NGw0MFhTWmQ1RWI4IiwidW5pb25pZCI6Im9UZlVKeE95SHRhV3gxVl9hN3ZyRXB2NmQ5Q1UiLCJvcGVuaWQiOiJvNUdCTzVmWlA0MzdKaEo3NGw0MFhTWmQ1RWI4IiwiaXNzIjoiaHR0cDpcL1wvbG9jYWxob3N0OjE0MDEiLCJhdXRob3JpdGllcyI6WyJ3ZWNoYXRfbWluaXByb2dyYW0iLCJzbnNhcGlfYmFzZSIsInNuc2FwaV9pbmZvIl0sImF1ZCI6Inh1eGlhb3dlaV9jbGllbnRfd2VjaGF0X21pbmlwcm9ncmFtX2lkIiwibmJmIjoxNjU3MjA3NjM0LCJzY29wZSI6WyJzbnNhcGlfYmFzZSIsInNuc2FwaV9pbmZvIl0sImFwcGlkIjoid3hjZjRmM2EyMTdhOGJjNzI4IiwiZXhwIjoxNjU3MjUwODM0LCJpYXQiOjE2NTcyMDc2MzQsInVzZXJuYW1lIjoibzVHQk81ZlpQNDM3SmhKNzRsNDBYU1pkNUViOCJ9.p51ICvOJXgQ60zPrbqkit7VXTcHSf_3RpORJCpzu4miKds0VPSjFGRTkmTEdtPMc9gvIUKU0JuG-wo7nq8yIotSzj0WEEfkMq44UsJEmMgBjLcfoNAkemrbSLXRScaMfJgcvUe9fy2D_KlwxwtxNRL6zlyuKjFwX9Gug8s7bjuKU86haoEmj_P2h2GKEm_p9YiFvruVFD15sKY7ng7uNzOlmBw-Ca-KaQcKGpH6jxMIskI_EN4WpO03MOcTJtuXS2Kz9-Vi5UmvqhsgQbtK966UJsLn86BHZnp0Pxjs6L1zYWEHM5SbHDEqdDI2QBOSINDII0eag8lcBejRXazD-ew");

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
		String json = objectWriter.writeValueAsString(jwt);
		log.info("\n" + json);
	}

}
