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
	 *   "tokenValue" : "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJvNUdCTzVmWlA0MzdKaEo3NGw0MFhTWmQ1RWI4IiwidW5pb25pZCI6Im9UZlVKeE95SHRhV3gxVl9hN3ZyRXB2NmQ5Q1UiLCJzZXNzaW9uS2V5IjoiQUVNWkwzM2d2dU82VTJUSGdONlY2QT09Iiwib3BlbmlkIjoibzVHQk81ZlpQNDM3SmhKNzRsNDBYU1pkNUViOCIsImlzcyI6Imh0dHA6XC9cLzEyNy4wLjAuMToxNDAxIiwiYXV0aG9yaXRpZXMiOlsid2VjaGF0X21pbmlwcm9ncmFtIiwic25zYXBpX2Jhc2UiXSwiYXVkIjoieHV4aWFvd2VpX2NsaWVudF93ZWJjaGF0X21pbmlwcm9ncmFtX2lkIiwibmJmIjoxNjU2NTIwMTM5LCJzY29wZSI6WyJzbnNhcGlfYmFzZSJdLCJhcHBpZCI6Ind4Y2Y0ZjNhMjE3YThiYzcyOCIsImV4cCI6MTY1NjU2MzMzOSwiaWF0IjoxNjU2NTIwMTM5LCJ1c2VybmFtZSI6Im81R0JPNWZaUDQzN0poSjc0bDQwWFNaZDVFYjgifQ.sJ-qXMwQPlrjrMaLW9WT3nZ64JrI7pN4Ds8dnu-dh9lKbErUJDyA2ND27LFFZ6EgESdLtZX3Pvm_qQuqfs8Zmwt2-yBkqlZHUTMQBbRqXvRv4TSqj5n9JW9aq4tBQnrYF9KfshIzJhecvt2XI8iHi1ggdDuNo004ffTvc5ZYbAUAz_Jy_wDbN1-E4JHlxmJi9Fp2DIerol5m-SF5oVntDrDVDBA9pkiLs4TXL-zUobNYYjmShXKUOlKTTLz4wsEFEmnjy_s-UyQjumZxxbfpR6ePci7trakq8LSwLHuQagXq_NZ5aMczM-2HphOwq-FxqJ4uwNm07KCrN9yiuvRTVg",
	 *   "issuedAt" : 1656520139.000000000,
	 *   "expiresAt" : 1656563339.000000000,
	 *   "headers" : {
	 *     "alg" : "RS256"
	 *   },
	 *   "claims" : {
	 *     "sub" : "o5GBO5fZP437JhJ74l40XSZd5Eb8",
	 *     "unionid" : "oTfUJxOyHtaWx1V_a7vrEpv6d9CU",
	 *     "sessionKey" : "AEMZL33gvuO6U2THgN6V6A==",
	 *     "openid" : "o5GBO5fZP437JhJ74l40XSZd5Eb8",
	 *     "iss" : "http://127.0.0.1:1401",
	 *     "authorities" : [ "wechat_miniprogram", "snsapi_base" ],
	 *     "aud" : [ "xuxiaowei_client_webchat_miniprogram_id" ],
	 *     "nbf" : 1656520139.000000000,
	 *     "scope" : [ "snsapi_base" ],
	 *     "appid" : "wxcf4f3a217a8bc728",
	 *     "exp" : 1656563339.000000000,
	 *     "iat" : 1656520139.000000000,
	 *     "username" : "o5GBO5fZP437JhJ74l40XSZd5Eb8"
	 *   },
	 *   "id" : null,
	 *   "notBefore" : 1656520139.000000000,
	 *   "issuer" : "http://127.0.0.1:1401",
	 *   "subject" : "o5GBO5fZP437JhJ74l40XSZd5Eb8",
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
				"eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJvNUdCTzVmWlA0MzdKaEo3NGw0MFhTWmQ1RWI4IiwidW5pb25pZCI6Im9UZlVKeE95SHRhV3gxVl9hN3ZyRXB2NmQ5Q1UiLCJzZXNzaW9uS2V5IjoiQUVNWkwzM2d2dU82VTJUSGdONlY2QT09Iiwib3BlbmlkIjoibzVHQk81ZlpQNDM3SmhKNzRsNDBYU1pkNUViOCIsImlzcyI6Imh0dHA6XC9cLzEyNy4wLjAuMToxNDAxIiwiYXV0aG9yaXRpZXMiOlsid2VjaGF0X21pbmlwcm9ncmFtIiwic25zYXBpX2Jhc2UiXSwiYXVkIjoieHV4aWFvd2VpX2NsaWVudF93ZWJjaGF0X21pbmlwcm9ncmFtX2lkIiwibmJmIjoxNjU2NTIwMTM5LCJzY29wZSI6WyJzbnNhcGlfYmFzZSJdLCJhcHBpZCI6Ind4Y2Y0ZjNhMjE3YThiYzcyOCIsImV4cCI6MTY1NjU2MzMzOSwiaWF0IjoxNjU2NTIwMTM5LCJ1c2VybmFtZSI6Im81R0JPNWZaUDQzN0poSjc0bDQwWFNaZDVFYjgifQ.sJ-qXMwQPlrjrMaLW9WT3nZ64JrI7pN4Ds8dnu-dh9lKbErUJDyA2ND27LFFZ6EgESdLtZX3Pvm_qQuqfs8Zmwt2-yBkqlZHUTMQBbRqXvRv4TSqj5n9JW9aq4tBQnrYF9KfshIzJhecvt2XI8iHi1ggdDuNo004ffTvc5ZYbAUAz_Jy_wDbN1-E4JHlxmJi9Fp2DIerol5m-SF5oVntDrDVDBA9pkiLs4TXL-zUobNYYjmShXKUOlKTTLz4wsEFEmnjy_s-UyQjumZxxbfpR6ePci7trakq8LSwLHuQagXq_NZ5aMczM-2HphOwq-FxqJ4uwNm07KCrN9yiuvRTVg");

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
		String json = objectWriter.writeValueAsString(jwt);
		log.info("\n" + json);
	}

}
