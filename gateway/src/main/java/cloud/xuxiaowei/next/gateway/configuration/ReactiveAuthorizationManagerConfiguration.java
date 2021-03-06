package cloud.xuxiaowei.next.gateway.configuration;

import cloud.xuxiaowei.next.core.properties.CloudWhiteListProperties;
import cloud.xuxiaowei.next.core.properties.JwkKeyProperties;
import cloud.xuxiaowei.next.gateway.filter.CorsBeforeWebFilter;
import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.IpAddressMatcher;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

/**
 * ????????????????????????
 *
 * @author xuxiaowei
 * @see EnableWebFluxSecurity
 * @see AuthenticationWebFilter ???????????? Web ??????????????????
 * <code>http.addFilterAt(filter, SecurityWebFiltersOrder.AUTHENTICATION);</code>
 * @see ServerBearerTokenAuthenticationConverter
 * @see ReactiveUserDetailsServiceAutoConfiguration
 * @since 0.0.1
 */
@Slf4j
@Configuration
@EnableWebFluxSecurity
public class ReactiveAuthorizationManagerConfiguration implements ReactiveAuthorizationManager<AuthorizationContext> {

	private ServerAuthenticationEntryPoint serverAuthenticationEntryPoint;

	private ServerAccessDeniedHandler serverAccessDeniedHandler;

	private CorsBeforeWebFilter corsBeforeWebFilter;

	private CloudWhiteListProperties cloudWhiteListProperties;

	private JwkKeyProperties jwkKeyProperties;

	private OAuth2AuthorizationServiceReactiveJwtAuthenticationConverter jwtAuthenticationConverter;

	@Autowired
	public void setServerAuthenticationEntryPoint(ServerAuthenticationEntryPoint serverAuthenticationEntryPoint) {
		this.serverAuthenticationEntryPoint = serverAuthenticationEntryPoint;
	}

	@Autowired
	public void setServerAccessDeniedHandler(ServerAccessDeniedHandler serverAccessDeniedHandler) {
		this.serverAccessDeniedHandler = serverAccessDeniedHandler;
	}

	@Autowired
	public void setCorsBeforeWebFilter(CorsBeforeWebFilter corsBeforeWebFilter) {
		this.corsBeforeWebFilter = corsBeforeWebFilter;
	}

	@Autowired
	public void setCloudWhiteListProperties(CloudWhiteListProperties cloudWhiteListProperties) {
		this.cloudWhiteListProperties = cloudWhiteListProperties;
	}

	@Autowired
	public void setJwkKeyProperties(JwkKeyProperties jwkKeyProperties) {
		this.jwkKeyProperties = jwkKeyProperties;
	}

	@Autowired
	public void setJwtAuthenticationConverter(
			OAuth2AuthorizationServiceReactiveJwtAuthenticationConverter jwtAuthenticationConverter) {
		this.jwtAuthenticationConverter = jwtAuthenticationConverter;
	}

	/**
	 * ?????????????????????????????????????????????
	 */
	@Autowired
	public void setSecurityProperties(SecurityProperties securityProperties) {
		securityProperties.getUser().setPassword(UUID.randomUUID().toString());
	}

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

		// ????????????????????????
		http.authorizeExchange().anyExchange().access(this);

		// ???????????? ?????????????????? CSRF
		// ????????????????????????
		http.csrf().disable();

		// ?????? form ??????
		http.formLogin().disable();

		http.oauth2ResourceServer(oauth2ResourceServerCustomizer -> {

			// ????????????????????????
			// ?????? OAuth2 JWT ?????????????????????
			RSAPublicKey rsaPublicKey = jwkKeyProperties.rsaPublicKey();
			oauth2ResourceServerCustomizer.jwt().publicKey(rsaPublicKey);

			// ??????????????????????????????Token
			// ?????????????????????????????????????????????????????????????????????
			oauth2ResourceServerCustomizer.jwt().jwtAuthenticationConverter(jwtAuthenticationConverter);

			// ????????????????????????????????????Token?????????
			oauth2ResourceServerCustomizer.authenticationEntryPoint(serverAuthenticationEntryPoint);
		});

		// ????????????????????? CORS ?????? ????????? <code>http.addFilterBefore(?????????,
		// SecurityWebFiltersOrder.CORS);</code>

		// ??? CORS ????????????
		http.addFilterBefore(corsBeforeWebFilter, SecurityWebFiltersOrder.CORS);

		// ???????????????????????? URI ?????????????????????????????????????????? {@code false}???
		ServerBearerTokenAuthenticationConverter bearerTokenConverter = new ServerBearerTokenAuthenticationConverter();
		bearerTokenConverter.setAllowUriQueryParameter(true);
		http.oauth2ResourceServer().bearerTokenConverter(bearerTokenConverter);

		// ?????????????????????
		http.exceptionHandling().authenticationEntryPoint(serverAuthenticationEntryPoint);
		// ????????????????????????????????????
		http.oauth2ResourceServer().accessDeniedHandler(serverAccessDeniedHandler);

		return http.build();
	}

	@Override
	public Mono<AuthorizationDecision> check(Mono<Authentication> authentication,
			AuthorizationContext authorizationContext) {

		ServerWebExchange exchange = authorizationContext.getExchange();
		ServerHttpRequest request = exchange.getRequest();
		HttpMethod method = request.getMethod();
		URI uri = request.getURI();

		// ?????????????????????ID
		String requestId = request.getId();
		MDC.put(Constant.REQUEST_ID, requestId);

		log.debug("?????????{}???{}", method, uri);

		boolean whiteList = whiteList(exchange);
		if (whiteList) {
			return Mono.just(new AuthorizationDecision(true));
		}

		return authentication.map(requestAuthentication -> {

			// ?????????????????????
			if (requestAuthentication.isAuthenticated()) {
				// ??????
				return new AuthorizationDecision(true);
			}
			else {
				// ?????????????????????
				// ????????????
				return new AuthorizationDecision(false);
			}
		})
				// ???????????????
				// ????????????
				.defaultIfEmpty(new AuthorizationDecision(false));
	}

	/**
	 * ???????????????
	 * @param exchange ?????????????????????
	 * @return ??????????????????
	 */
	private boolean whiteList(ServerWebExchange exchange) {

		ServerHttpRequest request = exchange.getRequest();
		HttpMethod method = request.getMethod();
		URI uri = request.getURI();
		String path = uri.getPath();

		if (method.matches(HttpMethod.OPTIONS.name())) {
			log.debug("?????????{}???{}", method, path);
			return true;
		}

		AntPathMatcher antPathMatcher = new AntPathMatcher();

		List<String> ignores = cloudWhiteListProperties.getIgnores();
		for (String ignore : ignores) {
			boolean match = antPathMatcher.match(ignore, path);
			if (match) {
				return true;
			}
		}

		InetSocketAddress remoteAddress = request.getRemoteAddress();

		if (remoteAddress == null) {
			return false;
		}

		InetAddress address = remoteAddress.getAddress();
		String hostAddress = address.getHostAddress();

		List<String> actuatorIpList = cloudWhiteListProperties.getActuatorIpList();

		// ????????????IP????????????
		// ?????????/actuator/**???/xxx/actuator/**
		boolean matchActuator = antPathMatcher.match("/**/actuator/**", path);
		if (matchActuator) {
			for (String actuatorIp : actuatorIpList) {
				IpAddressMatcher ipAddressMatcher = new IpAddressMatcher(actuatorIp);
				boolean matches = ipAddressMatcher.matches(hostAddress);
				if (matches) {
					return true;
				}
			}
		}

		List<CloudWhiteListProperties.Service> services = cloudWhiteListProperties.getServices();
		for (CloudWhiteListProperties.Service service : services) {
			String name = service.getName();
			List<String> pathList = service.getPathList();
			for (String p : pathList) {
				String pattern = name.startsWith("/") ? name : "/" + name;
				pattern = p.startsWith("/") ? pattern + p : pattern + "/" + p;
				boolean match = antPathMatcher.match(pattern, path);
				if (match) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public Mono<Void> verify(Mono<Authentication> authentication, AuthorizationContext object) {
		return ReactiveAuthorizationManager.super.verify(authentication, object);
	}

}
