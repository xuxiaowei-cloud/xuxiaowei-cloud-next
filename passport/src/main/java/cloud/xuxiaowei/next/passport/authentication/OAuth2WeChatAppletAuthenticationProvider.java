package cloud.xuxiaowei.next.passport.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.*;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.ProviderContextHolder;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.*;

import static cloud.xuxiaowei.next.passport.authentication.OAuth2WeChatAppletAuthenticationToken.WECHAT_APPLET;

/**
 * 微信 OAuth2 身份验证提供程序
 *
 * @author xuxiaowei
 * @since 0.0.1
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
 */
@Slf4j
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class OAuth2WeChatAppletAuthenticationProvider implements AuthenticationProvider {

	private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

	private WeChatApplet weChatApplet;

	private OAuth2AuthorizationService authorizationService;

	private OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

	public void setAuthorizationService(OAuth2AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	public void setWeChatApplet(WeChatApplet weChatApplet) {
		this.weChatApplet = weChatApplet;
	}

	public void setTokenGenerator(OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
		this.tokenGenerator = tokenGenerator;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		OAuth2WeChatAppletAuthenticationToken oauth2WeChatAppletAuthenticationToken = (OAuth2WeChatAppletAuthenticationToken) authentication;

		OAuth2ClientAuthenticationToken clientPrincipal = OAuth2ProviderUtils
				.getAuthenticatedClientElseThrowInvalidClient(oauth2WeChatAppletAuthenticationToken);
		RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();

		String appid = oauth2WeChatAppletAuthenticationToken.getAppid();
		String code = oauth2WeChatAppletAuthenticationToken.getCode();

		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> uriVariables = new HashMap<>(8);
		uriVariables.put("appid", appid);

		String secret = weChatApplet.getSecretByAppid(appid);

		uriVariables.put("secret", secret);
		// https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/access-token/auth.getAccessToken.html
		// Map map = restTemplate.getForObject(
		// "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={secret}",
		// Map.class, uriVariables);
		// String o = map.get("access_token") + "";

		// https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html
		uriVariables.put("js_code", code);
		String forObject = restTemplate.getForObject(
				"https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={js_code}&grant_type=authorization_code",
				String.class, uriVariables);

		Map map;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			map = objectMapper.readValue(forObject, Map.class);
		}
		catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		String openid = map.get("openid") + "";

		OAuth2Authorization.Builder builder = OAuth2Authorization.withRegisteredClient(registeredClient);
		builder.principalName(openid);
		builder.authorizationGrantType(WECHAT_APPLET);

		List<GrantedAuthority> authorities = new ArrayList<>();
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("wechat_applet");
		authorities.add(authority);
		User user = new User(openid, code, authorities);
		Object details = oauth2WeChatAppletAuthenticationToken.getDetails();
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				user, details);

		builder.attribute(Principal.class.getName(), usernamePasswordAuthenticationToken);
		Set<String> scopes = new HashSet<>();
		scopes.add("snsapi_base");
		builder.attribute(OAuth2Authorization.AUTHORIZED_SCOPE_ATTRIBUTE_NAME, scopes);

		OAuth2Authorization authorization = builder.build();

		// @formatter:off
		DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
				.registeredClient(registeredClient)
				.principal(authorization.getAttribute(Principal.class.getName()))
				.providerContext(ProviderContextHolder.getProviderContext())
				.authorization(authorization)
				.authorizedScopes(authorization.getAttribute(OAuth2Authorization.AUTHORIZED_SCOPE_ATTRIBUTE_NAME))
				.authorizationGrantType(WECHAT_APPLET)
				.authorizationGrant(oauth2WeChatAppletAuthenticationToken);
		// @formatter:on

		OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.from(authorization);

		// ----- Access token -----
		OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
		OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
		if (generatedAccessToken == null) {
			OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
					"The token generator failed to generate the access token.", ERROR_URI);
			throw new OAuth2AuthenticationException(error);
		}
		OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
				generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
				generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());
		if (generatedAccessToken instanceof ClaimAccessor) {
			authorizationBuilder.token(accessToken,
					(metadata) -> metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME,
							((ClaimAccessor) generatedAccessToken).getClaims()));
		}
		else {
			authorizationBuilder.accessToken(accessToken);
		}

		// ----- Refresh token -----
		OAuth2RefreshToken refreshToken = null;
		if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN) &&
		// Do not issue refresh token to public client
				!clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {

			tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
			OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
			if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
				OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
						"The token generator failed to generate the refresh token.", ERROR_URI);
				throw new OAuth2AuthenticationException(error);
			}
			refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
			authorizationBuilder.refreshToken(refreshToken);
		}

		authorization = authorizationBuilder.build();

		this.authorizationService.save(authorization);

		Map<String, Object> additionalParameters = Collections.emptyMap();

		return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken, refreshToken,
				additionalParameters);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return OAuth2WeChatAppletAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
