package cloud.xuxiaowei.next.passport.authentication;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.*;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.stereotype.Component;

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
@Component
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class OAuth2WeChatAuthenticationProvider implements AuthenticationProvider {

	private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

	private JwtGenerator jwtGenerator;

	private OAuth2AuthorizationService authorizationService;

	@Lazy
	@Autowired
	public void setJwtGenerator(JWKSource<SecurityContext> jwkSource) {
		JwtEncoder jwtEncoder = new NimbusJwtEncoder(jwkSource);
		this.jwtGenerator = new JwtGenerator(jwtEncoder);
	}

	@Lazy
	@Autowired
	public void setAuthorizationService(OAuth2AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		log.error("暂未完成开发");

		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return OAuth2WeChatAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
