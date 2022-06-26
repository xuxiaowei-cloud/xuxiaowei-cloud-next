package org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

/**
 * OAuth2 工具类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class OAuth2Utils {

	public static <B extends HttpSecurityBuilder<B>> RegisteredClientRepository getRegisteredClientRepository(
			B builder) {
		return OAuth2ConfigurerUtils.getRegisteredClientRepository(builder);
	}

	public static <B extends HttpSecurityBuilder<B>> OAuth2AuthorizationService getAuthorizationService(B builder) {
		return OAuth2ConfigurerUtils.getAuthorizationService(builder);
	}

	public static <B extends HttpSecurityBuilder<B>> OAuth2AuthorizationConsentService getAuthorizationConsentService(
			B builder) {
		return OAuth2ConfigurerUtils.getAuthorizationConsentService(builder);
	}

	public static <B extends HttpSecurityBuilder<B>> OAuth2TokenGenerator<? extends OAuth2Token> getTokenGenerator(
			B builder) {
		return OAuth2ConfigurerUtils.getTokenGenerator(builder);
	}

}
