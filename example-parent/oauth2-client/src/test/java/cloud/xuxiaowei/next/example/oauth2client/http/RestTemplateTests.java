package cloud.xuxiaowei.next.example.oauth2client.http;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link RestTemplate} 示例
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
class RestTemplateTests {

	/**
	 * 客户端凭证模式：client_credentials
	 */
	@Test
	void clientCredentials() {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> map = new HashMap<>(8);
		map.put("client_id", "xuxiaowei_client_id");
		map.put("client_secret", "xuxiaowei_client_secret");
		map.put("scope", "snsapi_base snsapi_info");

		HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

		String accessTokenUri = "http://localhost:1401/oauth2/token"
				+ "?client_id={client_id}&client_secret={client_secret}&grant_type=client_credentials&scope={scope}";

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(accessTokenUri, httpEntity, String.class,
				map);

		HttpStatusCode statusCode = responseEntity.getStatusCode();
		log.info(String.valueOf(statusCode));
		log.info(responseEntity.getBody());
	}

}
