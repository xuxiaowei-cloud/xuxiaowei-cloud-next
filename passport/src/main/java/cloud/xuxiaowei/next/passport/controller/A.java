package cloud.xuxiaowei.next.passport.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class A {

	private static JwtEncoder createEncoder(String secret) {
		SecretKey secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
		OctetSequenceKey secretKeyJwk = new OctetSequenceKey.Builder(secretKey).build();
		JWKSource<SecurityContext> jwkSource = (jwkSelector, securityContext) -> jwkSelector
				.select(new JWKSet(secretKeyJwk));
		return new NimbusJwtEncoder(jwkSource);
	}

	@SneakyThrows
	@RequestMapping("/a")
	public String a() {
		String clientId = "ca920f1d-5d57-4697-a778-79fb8362b697";
		String clientSecret = "e5b16dd6-cbc5-4d54-a443-1912d8ce2107";
		String clientAssertionType = "urn:ietf:params:oauth:client-assertion-type:jwt-bearer";
		String scope = "snsapi_base snsapi_info";

		JwtEncoder jwtEncoder = createEncoder(clientSecret);
		// @formatter:off
		JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
		JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
				.issuer(clientId)
				.subject(clientId)
				.audience(Collections.singletonList(UriComponentsBuilder
						.fromUriString("http://127.0.0.1:1401")
						.path("/oauth2/token")
						.build()
						.toUriString()))
				.build();
		// @formatter:on
		JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(jwsHeader, jwtClaimsSet);
		Jwt jwt = jwtEncoder.encode(jwtEncoderParameters);
		String clientAssertion = jwt.getTokenValue();

		Map<String, String> map = new HashMap<>(8);
		map.put("client_id", clientId);
		map.put("client_assertion_type", clientAssertionType);
		map.put("client_assertion", clientAssertion);
		map.put("scope", scope);

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

		String accessTokenUri = "http://localhost:1401/oauth2/token"
				+ "?client_id={client_id}&client_assertion_type={client_assertion_type}&client_assertion={client_assertion}&grant_type=client_credentials&scope={scope}";

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(accessTokenUri, httpEntity, String.class,
				map);

		HttpStatusCode statusCode = responseEntity.getStatusCode();
		log.info(String.valueOf(statusCode));
		log.info(responseEntity.getBody());

		return responseEntity.getBody();
	}

}
