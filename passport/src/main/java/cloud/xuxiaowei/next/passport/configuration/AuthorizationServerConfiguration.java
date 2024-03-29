package cloud.xuxiaowei.next.passport.configuration;

import cloud.xuxiaowei.next.core.properties.CloudClientProperties;
import cloud.xuxiaowei.next.core.properties.CloudJwkKeyProperties;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.endpoint.OAuth2WeChatMiniProgramParameterNames;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.authentication.*;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2TokenEndpointConfigurer;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
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
 * 授权服务器配置
 *
 * @author xuxiaowei
 * @see <a href="http://127.0.0.1:1301/.well-known/oauth-authorization-server">OAuth 2.0
 * 授权服务器元数据请求的默认端点URI</a>
 * @see <a href=
 * "http://127.0.0.1:1301/oauth2/authorize?client_id=xuxiaowei_client_id&redirect_uri=http://127.0.0.1:1401/code&response_type=code&scope=snsapi_base&state=beff3dfc-bad8-40db-b25f-e5459e3d6ad7">/oauth2/authorize</a>
 * @see <a href=
 * "https://docs.spring.io/spring-security/reference/6.0.0-M3/servlet/oauth2/resource-server/jwt.html#oauth2resourceserver-jwt-authorization-extraction">手动提取权限-6.0.0-M3</a>
 * @see <a href=
 * "https://github.com/spring-projects/spring-authorization-server/issues/181#issuecomment-756913042">将用户权限作为声明传播`Jwt`是一种自定义行为</a>
 * @see <a href=
 * "https://github.com/spring-projects/spring-authorization-server/blob/a30a1692b28915947a001f1e2a6d1e41a550eaa7/oauth2-authorization-server/src/test/java/org/springframework/security/config/annotation/web/configurers/oauth2/server/authorization/OidcTests.java#L264">自定义
 * Jwt 声明和标头官方示例代码</a>
 * @see <a href=
 * "https://github.com/spring-projects/spring-authorization-server/issues/199">自定义 Jwt
 * 声明和标头需要更灵活 议题</a>
 * @since 0.0.1
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfiguration {

	private CloudJwkKeyProperties cloudJwkKeyProperties;

	private CloudClientProperties cloudClientProperties;

	private IUsersService usersService;

	@Autowired
	public void setCloudJwkKeyProperties(CloudJwkKeyProperties cloudJwkKeyProperties) {
		this.cloudJwkKeyProperties = cloudJwkKeyProperties;
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
	 * "https://docs.spring.io/spring-authorization-server/docs/current/reference/html/protocol-endpoints.html">协议端点的</a>
	 * Spring Security 过滤器链。
	 * @see OAuth2AuthorizationServerConfiguration#applyDefaultSecurity(HttpSecurity) 默认
	 * OAuth 2.1 授权配置
	 * @see OAuth2AuthorizationEndpointFilter 默认 OAuth 2.1 授权页面
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

		OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
		authorizationServerConfigurer
			.authorizationEndpoint(authorizationEndpointCustomizer -> authorizationEndpointCustomizer
				.consentPage(cloudClientProperties.getConsentPage()));

		RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

		// @formatter:off
		http.securityMatcher(endpointsMatcher)
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
				.csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
				.apply(authorizationServerConfigurer);
		// @formatter:on

		// 自定义客户授权
		authorizationServerConfigurer.tokenEndpoint(tokenEndpointCustomizer -> tokenEndpointCustomizer
			.accessTokenRequestConverter(new DelegatingAuthenticationConverter(Arrays.asList(
					// 新增：微信 OAuth2 用于验证授权授予的 {@link
					// OAuth2WeChatMiniProgramAuthenticationToken}
					new OAuth2WeChatMiniProgramAuthenticationConverter(),
					// 默认值：OAuth2 授权码认证转换器
					new OAuth2AuthorizationCodeAuthenticationConverter(),
					// 默认值：OAuth2 刷新令牌认证转换器
					new OAuth2RefreshTokenAuthenticationConverter(),
					// 默认值：OAuth2 客户端凭据身份验证转换器
					new OAuth2ClientCredentialsAuthenticationConverter())))
			// 用于处理失败的身份验证尝试的策略。
			.errorResponseHandler(new AccessTokenAuthenticationFailureHandlerImpl()));

		// 微信小程序 OAuth2 身份验证提供程序
		new OAuth2WeChatMiniProgramAuthenticationProvider(http);

		return http.build();
	}

	/**
	 * @see RegisteredClientRepository 用于管理客户端的实例。
	 */
	@Bean
	public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
		return new JdbcRegisteredClientRepository(jdbcTemplate);
	}

	/**
	 * 授权码、授权Token、刷新Token 持久化
	 */
	@Bean
	public OAuth2AuthorizationService oauth2AuthorizationService(JdbcOperations jdbcOperations,
			RegisteredClientRepository registeredClientRepository) {
		return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
	}

	/**
	 * 授权 持久化
	 */
	@Bean
	public OAuth2AuthorizationConsentService oauth2AuthorizationConsentService(JdbcOperations jdbcOperations,
			RegisteredClientRepository registeredClientRepository) {
		return new JdbcOAuth2AuthorizationConsentService(jdbcOperations, registeredClientRepository);
	}

	/**
	 * @see JWKSource 用于签署访问令牌的实例。
	 */
	@Bean
	public JWKSource<SecurityContext> jwkSource() {
		// @formatter:off
		RSAKey rsaKey = new RSAKey
				.Builder(cloudJwkKeyProperties.rsaPublicKey())
				.privateKey(cloudJwkKeyProperties.privateKey())
				.build();
		// @formatter:on
		JWKSet jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
	}

	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	/**
	 * JWK 增加用户权限
	 *
	 * @see <a href=
	 * "https://github.com/spring-projects/spring-authorization-server/issues/181#issuecomment-756913042">将用户权限作为声明传播`Jwt`是一种自定义行为</a>
	 * @see <a href=
	 * "https://github.com/spring-projects/spring-authorization-server/blob/a30a1692b28915947a001f1e2a6d1e41a550eaa7/oauth2-authorization-server/src/test/java/org/springframework/security/config/annotation/web/configurers/oauth2/server/authorization/OidcTests.java#L264">自定义
	 * Jwt 声明和标头官方示例代码</a>
	 * @see <a href=
	 * "https://github.com/spring-projects/spring-authorization-server/issues/199">自定义 Jwt
	 * 声明和标头需要更灵活 议题</a>
	 */
	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
		return context -> {
			if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {

				// JWT 构建器
				JwtClaimsSet.Builder claims = context.getClaims();

				// 用户认证
				Authentication principal = context.getPrincipal();

				// 放入用户名
				String name = principal.getName();
				claims.claim(Constant.USERNAME, name);

				// 放入用户ID
				Users users = usersService.getByUsername(name);
				if (users != null) {
					// The class with java.lang.Long and name of java.lang.Long is not in
					// the allowlist.
					// If you believe this class is safe to deserialize, please provide an
					// explicit mapping using Jackson annotations or by providing a Mixin.
					// If the serialization is only done by a trusted source, you can also
					// enable default typing.
					// See https://github.com/spring-projects/spring-security/issues/4370
					// for details
					claims.claim(Constant.USERS_ID, users.getUsersId() + "");
				}

				// 用户权限
				Set<String> authorities = principal.getAuthorities()
					.stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.toSet());

				// 客户权限
				Set<String> authorizedScopes = context.getAuthorizedScopes();

				// 合并权限
				authorities.addAll(authorizedScopes);

				// 将合并权限放入 JWT 中
				claims.claim(Constant.AUTHORITIES, authorities);

				/// 微信用户的权限特殊处理
				// 增加微信特有数据
				if (principal instanceof WeChatMiniProgramAuthenticationToken authenticationToken) {
					// 微信小程序的appid，不能为空
					String appid = authenticationToken.getAppid();
					// 用户唯一标识，不能为空
					String openid = authenticationToken.getOpenid();
					// 用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回，详见 <a
					// href="https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/union-id.html">UnionID
					// 机制说明</a>。
					String unionid = authenticationToken.getUnionid();
					claims.claim(OAuth2WeChatMiniProgramParameterNames.APPID, appid);
					claims.claim(OAuth2WeChatMiniProgramParameterNames.OPENID, openid);
					claims.claim(OAuth2WeChatMiniProgramParameterNames.UNIONID, unionid);
				}
			}
		};
	}

	/**
	 * {@link AuthorizationServerSettings} 配置 Spring Authorization Server 的实例。
	 * @see <a href=
	 * "https://github.com/spring-projects/spring-authorization-server/commit/c60ae4532f1d745bff6eb793113731aba0493b70">Rename
	 * ProviderSettings</a>
	 */
	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().build();
	}

}
