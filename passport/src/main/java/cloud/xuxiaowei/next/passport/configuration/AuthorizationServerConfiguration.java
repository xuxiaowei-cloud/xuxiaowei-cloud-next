package cloud.xuxiaowei.next.passport.configuration;

import cloud.xuxiaowei.next.core.properties.CloudClientProperties;
import cloud.xuxiaowei.next.core.properties.JwkKeyProperties;
import cloud.xuxiaowei.next.passport.handler.AccessTokenAuthenticationFailureHandlerImpl;
import cloud.xuxiaowei.next.system.entity.Users;
import cloud.xuxiaowei.next.system.service.IUsersService;
import cloud.xuxiaowei.next.utils.Constant;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.WeChatMiniProgramAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2TokenEndpointConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2WeChatMiniProgramParameterNames;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.*;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.web.OAuth2AuthorizationEndpointFilter;
import org.springframework.security.oauth2.server.authorization.web.OAuth2TokenEndpointFilter;
import org.springframework.security.oauth2.server.authorization.web.authentication.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ?????????????????????
 *
 * @author xuxiaowei
 * @see <a href="http://127.0.0.1:1301/.well-known/oauth-authorization-server">OAuth 2.0
 * ?????????????????????????????????????????????URI</a>
 * @see <a href=
 * "http://127.0.0.1:1301/oauth2/authorize?client_id=xuxiaowei_client_id&redirect_uri=http://127.0.0.1:1401/code&response_type=code&scope=snsapi_base&state=beff3dfc-bad8-40db-b25f-e5459e3d6ad7">/oauth2/authorize</a>
 * @see <a href=
 * "https://docs.spring.io/spring-security/reference/6.0.0-M3/servlet/oauth2/resource-server/jwt.html#oauth2resourceserver-jwt-authorization-extraction">??????????????????-6.0.0-M3</a>
 * @see <a href=
 * "https://github.com/spring-projects/spring-authorization-server/issues/181#issuecomment-756913042">?????????????????????????????????`Jwt`????????????????????????</a>
 * @see <a href=
 * "https://github.com/spring-projects/spring-authorization-server/blob/a30a1692b28915947a001f1e2a6d1e41a550eaa7/oauth2-authorization-server/src/test/java/org/springframework/security/config/annotation/web/configurers/oauth2/server/authorization/OidcTests.java#L264">?????????
 * Jwt ?????????????????????????????????</a>
 * @see <a href=
 * "https://github.com/spring-projects/spring-authorization-server/issues/199">????????? Jwt
 * ?????????????????????????????? ??????</a>
 * @since 0.0.1
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfiguration {

	private JwkKeyProperties jwkKeyProperties;

	private CloudClientProperties cloudClientProperties;

	private IUsersService usersService;

	@Autowired
	public void setJwkKeyProperties(JwkKeyProperties jwkKeyProperties) {
		this.jwkKeyProperties = jwkKeyProperties;
	}

	@Autowired
	public void setCloudClientProperties(CloudClientProperties cloudClientProperties) {
		this.cloudClientProperties = cloudClientProperties;
	}

	@Autowired
	public void setUsersService(IUsersService usersService) {
		this.usersService = usersService;
	}

	/**
	 * @see <a href=
	 * "https://docs.spring.io/spring-authorization-server/docs/current/reference/html/protocol-endpoints.html">???????????????</a>
	 * Spring Security ???????????????
	 * @see OAuth2AuthorizationServerConfiguration#applyDefaultSecurity(HttpSecurity) ??????
	 * OAuth 2.1 ????????????
	 * @see OAuth2AuthorizationEndpointFilter ?????? OAuth 2.1 ????????????
	 *
	 * @see OAuth2TokenEndpointFilter#setAuthenticationConverter(AuthenticationConverter)
	 * @see OAuth2TokenEndpointConfigurer#accessTokenRequestConverter(AuthenticationConverter)
	 * @see OAuth2TokenEndpointConfigurer#authenticationProvider(AuthenticationProvider)
	 * @see AnonymousAuthenticationProvider
	 * @see JwtClientAssertionAuthenticationProvider
	 * @see ClientSecretAuthenticationProvider
	 * @see PublicClientAuthenticationProvider
	 * @see OAuth2AuthorizationCodeRequestAuthenticationProvider
	 * @see OAuth2AuthorizationCodeAuthenticationProvider
	 * @see OAuth2RefreshTokenAuthenticationProvider
	 * @see OAuth2ClientCredentialsAuthenticationProvider
	 * @see OAuth2TokenIntrospectionAuthenticationProvider
	 * @see OAuth2TokenRevocationAuthenticationProvider
	 * @see OidcUserInfoAuthenticationProvider
	 * @see HttpSecurity#authenticationProvider(AuthenticationProvider)
	 */
	@Bean
	@Order(-1)
	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {

		OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer<>();

		authorizationServerConfigurer
				.authorizationEndpoint(authorizationEndpointCustomizer -> authorizationEndpointCustomizer
						.consentPage(cloudClientProperties.getConsentPage()));

		RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

		// @formatter:off
		http.requestMatcher(endpointsMatcher)
				.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
				.csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
				.apply(authorizationServerConfigurer);
		// @formatter:on

		// ?????????????????????
		authorizationServerConfigurer.tokenEndpoint(tokenEndpointCustomizer -> tokenEndpointCustomizer
				.accessTokenRequestConverter(new DelegatingAuthenticationConverter(Arrays.asList(
						// ??????????????? OAuth2 ??????????????????????????? {@link
						// OAuth2WeChatMiniProgramAuthenticationToken}
						new OAuth2WeChatMiniProgramAuthenticationConverter(),
						// ????????????OAuth2 ????????????????????????
						new OAuth2AuthorizationCodeAuthenticationConverter(),
						// ????????????OAuth2 ???????????????????????????
						new OAuth2RefreshTokenAuthenticationConverter(),
						// ????????????OAuth2 ????????????????????????????????????
						new OAuth2ClientCredentialsAuthenticationConverter())))
				// ???????????????????????????????????????????????????
				.errorResponseHandler(new AccessTokenAuthenticationFailureHandlerImpl()));

		// ??????????????? OAuth2 ????????????????????????
		new OAuth2WeChatMiniProgramAuthenticationProvider(http);

		return http.build();
	}

	/**
	 * @see RegisteredClientRepository ?????????????????????????????????
	 */
	@Bean
	public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
		return new JdbcRegisteredClientRepository(jdbcTemplate);
	}

	/**
	 * ??????????????????Token?????????Token ?????????
	 */
	@Bean
	public OAuth2AuthorizationService oauth2AuthorizationService(JdbcOperations jdbcOperations,
			RegisteredClientRepository registeredClientRepository) {
		return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
	}

	/**
	 * ?????? ?????????
	 */
	@Bean
	public OAuth2AuthorizationConsentService oauth2AuthorizationConsentService(JdbcOperations jdbcOperations,
			RegisteredClientRepository registeredClientRepository) {
		return new JdbcOAuth2AuthorizationConsentService(jdbcOperations, registeredClientRepository);
	}

	/**
	 * @see JWKSource ????????????????????????????????????
	 */
	@Bean
	public JWKSource<SecurityContext> jwkSource() {
		// @formatter:off
		RSAKey rsaKey = new RSAKey
				.Builder(jwkKeyProperties.rsaPublicKey())
				.privateKey(jwkKeyProperties.privateKey())
				.build();
		// @formatter:on
		JWKSet jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
	}

	/**
	 * JWK ??????????????????
	 *
	 * @see <a href=
	 * "https://github.com/spring-projects/spring-authorization-server/issues/181#issuecomment-756913042">?????????????????????????????????`Jwt`????????????????????????</a>
	 * @see <a href=
	 * "https://github.com/spring-projects/spring-authorization-server/blob/a30a1692b28915947a001f1e2a6d1e41a550eaa7/oauth2-authorization-server/src/test/java/org/springframework/security/config/annotation/web/configurers/oauth2/server/authorization/OidcTests.java#L264">?????????
	 * Jwt ?????????????????????????????????</a>
	 * @see <a href=
	 * "https://github.com/spring-projects/spring-authorization-server/issues/199">????????? Jwt
	 * ?????????????????????????????? ??????</a>
	 */
	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
		return context -> {
			if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {

				// JWT ?????????
				JwtClaimsSet.Builder claims = context.getClaims();

				// ????????????
				Authentication principal = context.getPrincipal();

				// ???????????????
				String name = principal.getName();
				claims.claim(Constant.USERNAME, name);

				// ????????????ID
				Users users = usersService.getByUsername(name);
				if (users != null) {
					claims.claim(Constant.USERS_ID, users.getUsersId());
				}

				// ????????????
				Set<String> authorities = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
						.collect(Collectors.toSet());

				// ????????????
				Set<String> authorizedScopes = context.getAuthorizedScopes();

				// ????????????
				authorities.addAll(authorizedScopes);

				// ????????????????????? JWT ???
				claims.claim(Constant.AUTHORITIES, authorities);

				/// ?????????????????????????????????
				// ????????????????????????
				if (principal instanceof WeChatMiniProgramAuthenticationToken authenticationToken) {
					// ??????????????????appid???????????????
					String appid = authenticationToken.getAppid();
					// ?????????????????????????????????
					String openid = authenticationToken.getOpenid();
					// ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????? <a
					// href="https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/union-id.html">UnionID
					// ????????????</a>???
					String unionid = authenticationToken.getUnionid();
					claims.claim(OAuth2WeChatMiniProgramParameterNames.APPID, appid);
					claims.claim(OAuth2WeChatMiniProgramParameterNames.OPENID, openid);
					claims.claim(OAuth2WeChatMiniProgramParameterNames.UNIONID, unionid);
				}
			}
		};
	}

	/**
	 * {@link ProviderSettings} ?????? Spring Authorization Server ????????????
	 */
	@Bean
	public ProviderSettings providerSettings() {
		return ProviderSettings.builder().build();
	}

}
