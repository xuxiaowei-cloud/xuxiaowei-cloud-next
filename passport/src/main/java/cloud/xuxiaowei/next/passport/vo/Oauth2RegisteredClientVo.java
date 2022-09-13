package cloud.xuxiaowei.next.passport.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.ConfigurationSettingNames;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static cloud.xuxiaowei.next.passport.controller.Oauth2RegisteredClientController.ALGORITHM_SPLIT;
import static cloud.xuxiaowei.next.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * 客户表
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@Slf4j
public class Oauth2RegisteredClientVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String clientId;

	private String clientName;

	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	private LocalDateTime clientIdIssuedAt;

	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	private LocalDateTime clientSecretExpiresAt;

	private String clientAuthenticationMethods;

	public void setClientAuthenticationMethods(String clientAuthenticationMethods) {
		this.clientAuthenticationMethods = clientAuthenticationMethods;
		this.authenticationMethods = Splitter.on(",").splitToList(clientAuthenticationMethods);
	}

	private List<String> authenticationMethods;

	private String authorizationGrantTypes;

	public void setAuthorizationGrantTypes(String authorizationGrantTypes) {
		this.authorizationGrantTypes = authorizationGrantTypes;
		this.grantTypes = Splitter.on(",").splitToList(authorizationGrantTypes);
	}

	private List<String> grantTypes;

	private String redirectUris;

	private String scopes;

	public void setScopes(String scopes) {
		this.scopes = scopes;
		this.scopeList = Splitter.on(",").splitToList(scopes);
	}

	private List<String> scopeList;

	private String clientSettings;

	public void setClientSettings(String clientSettings) {
		this.clientSettings = clientSettings;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> map = objectMapper.readValue(clientSettings, new TypeReference<>() {
			});
			ClientSettings.Builder builder = ClientSettings.withSettings(map);
			ClientSettings build = builder.build();
			this.requireProofKey = build.isRequireProofKey();
			this.requireAuthorizationConsent = build.isRequireAuthorizationConsent();
			this.jwkSetUrl = build.getJwkSetUrl();

			List<String> tokenEndpointAuthenticationSigningAlgorithmList = build
					.getSetting(ConfigurationSettingNames.Client.TOKEN_ENDPOINT_AUTHENTICATION_SIGNING_ALGORITHM);
			if (tokenEndpointAuthenticationSigningAlgorithmList != null
					&& tokenEndpointAuthenticationSigningAlgorithmList.size() > 0) {
				String algorithmClasses = tokenEndpointAuthenticationSigningAlgorithmList.get(0);
				String algorithmName = tokenEndpointAuthenticationSigningAlgorithmList.get(1);
				this.tokenSigningAlgorithm = algorithmClasses + ALGORITHM_SPLIT + algorithmName;
			}
		}
		catch (Exception e) {
			log.error("客户配置转换异常", e);
		}
	}

	private String tokenSigningAlgorithm;

	private String jwkSetUrl;

	private Boolean requireProofKey;

	private Boolean requireAuthorizationConsent;

	private String tokenSettings;

	public void setTokenSettings(String tokenSettings) {
		this.tokenSettings = tokenSettings;
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			ClassLoader classLoader = JdbcRegisteredClientRepository.class.getClassLoader();
			List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
			objectMapper.registerModules(securityModules);
			objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());

			Map<String, Object> map = objectMapper.readValue(tokenSettings, new TypeReference<>() {
			});
			TokenSettings.Builder builder = TokenSettings.withSettings(map);
			TokenSettings build = builder.build();
			this.authorizationCodeTimeToLive = build.getAuthorizationCodeTimeToLive().getSeconds();
			this.accessTokenTimeToLive = build.getAccessTokenTimeToLive().getSeconds();
			this.refreshTokenTimeToLive = build.getRefreshTokenTimeToLive().getSeconds();
			OAuth2TokenFormat accessTokenFormat = build.getAccessTokenFormat();
			if (accessTokenFormat != null) {
				this.accessTokenFormat = accessTokenFormat.getValue();
			}
			this.reuseRefreshTokens = build.getSetting(ConfigurationSettingNames.Token.REUSE_REFRESH_TOKENS);

			SignatureAlgorithm signatureAlgorithm = build
					.getSetting(ConfigurationSettingNames.Token.ID_TOKEN_SIGNATURE_ALGORITHM);
			if (signatureAlgorithm != null) {
				this.tokenSignatureAlgorithm = signatureAlgorithm.getClass().getName() + ALGORITHM_SPLIT
						+ signatureAlgorithm.getName();
			}
		}
		catch (Exception e) {
			log.error("Token配置转换异常", e);
		}
	}

	private String tokenSignatureAlgorithm;

	private long authorizationCodeTimeToLive;

	private long accessTokenTimeToLive;

	private long refreshTokenTimeToLive;

	private Boolean reuseRefreshTokens;

	private String accessTokenFormat;

}
