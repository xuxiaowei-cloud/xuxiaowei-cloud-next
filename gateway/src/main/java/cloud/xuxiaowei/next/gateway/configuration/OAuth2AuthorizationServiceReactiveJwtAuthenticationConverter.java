package cloud.xuxiaowei.next.gateway.configuration;

import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.SecurityUtils;
import org.slf4j.MDC;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * JWT {@link OAuth2AuthorizationService}
 *
 * @author xuxiaowei
 * @since 6.0.0
 * @see JwtAuthenticationConverter
 * @see ReactiveJwtAuthenticationConverter
 * @see OAuth2AuthorizationService
 */
@Component
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class OAuth2AuthorizationServiceReactiveJwtAuthenticationConverter
		implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

	private final OAuth2AuthorizationService authorizationService;

	private Converter<Jwt, Flux<GrantedAuthority>> jwtGrantedAuthoritiesConverter = new ReactiveJwtGrantedAuthoritiesConverterAdapter(
			new JwtGrantedAuthoritiesConverter());

	public OAuth2AuthorizationServiceReactiveJwtAuthenticationConverter(
			OAuth2AuthorizationService authorizationService) {
		Assert.notNull(authorizationService, "authorizationService cannot be null");
		this.authorizationService = authorizationService;
	}

	@Override
	public Mono<AbstractAuthenticationToken> convert(@NonNull Jwt jwt) {

		// 将当前用户名放入日志中
		String userName = SecurityUtils.getUserName(jwt);
		// 将当前用户ID放入日志中
		Long usersId = SecurityUtils.getUsersId(jwt);

		MDC.put(Constant.NAME, userName);
		MDC.put(Constant.USERS_ID, String.valueOf(usersId));

		String tokenValue = jwt.getTokenValue();
		OAuth2Authorization authorization = authorizationService.findByToken(tokenValue, OAuth2TokenType.ACCESS_TOKEN);
		if (authorization == null) {
			throw new InvalidBearerTokenException(tokenValue + " not found in OAuth2AuthorizationService");
		}

		// @formatter:off
		return Objects.requireNonNull(this.jwtGrantedAuthoritiesConverter.convert(jwt))
				.collectList()
				.map((authorities) -> new JwtAuthenticationToken(jwt, authorities));
		// @formatter:on
	}

	public void setJwtGrantedAuthoritiesConverter(
			Converter<Jwt, Flux<GrantedAuthority>> jwtGrantedAuthoritiesConverter) {
		Assert.notNull(jwtGrantedAuthoritiesConverter, "jwtGrantedAuthoritiesConverter cannot be null");
		this.jwtGrantedAuthoritiesConverter = jwtGrantedAuthoritiesConverter;
	}

}
