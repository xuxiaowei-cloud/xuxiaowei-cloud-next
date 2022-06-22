package cloud.xuxiaowei.next.gateway.configuration;

import cloud.xuxiaowei.next.core.properties.CloudWhiteListProperties;
import cloud.xuxiaowei.next.core.properties.JwkKeyProperties;
import cloud.xuxiaowei.next.gateway.filter.CorsBeforeWebFilter;
import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.IpAddressMatcher;
import cloud.xuxiaowei.next.utils.SecurityUtils;
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
 * 反应式授权管理器
 *
 * @author xuxiaowei
 * @see EnableWebFluxSecurity
 * @see AuthenticationWebFilter 身份验证 Web 过滤器，等级
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

	/**
	 * 禁止控制室台输出默认用户的密码
	 */
	@Autowired
	public void setSecurityProperties(SecurityProperties securityProperties) {
		securityProperties.getUser().setPassword(UUID.randomUUID().toString());
	}

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

		// 反应式授权管理器
		http.authorizeExchange().anyExchange().access(this);

		// 临时禁用 跨站请求伪造 CSRF
		// 待转化为配置文件
		http.csrf().disable();

		// 禁用 form 登录
		http.formLogin().disable();

		// 资源服务配置秘钥
		// 启用 OAuth2 JWT 资源服务器支持
		RSAPublicKey rsaPublicKey = jwkKeyProperties.rsaPublicKey();
		http.oauth2ResourceServer().jwt().publicKey(rsaPublicKey);

		// 资源服务异常切入点（验证Token异常）
		http.oauth2ResourceServer().authenticationEntryPoint(serverAuthenticationEntryPoint);

		// 自定义动态跨域 CORS 配置 过滤器 <code>http.addFilterBefore(过滤器,
		// SecurityWebFiltersOrder.CORS);</code>

		// 在 CORS 之前执行
		http.addFilterBefore(corsBeforeWebFilter, SecurityWebFiltersOrder.CORS);

		// 设置是否支持使用 URI 查询参数传输访问令牌。默认为 {@code false}。
		ServerBearerTokenAuthenticationConverter bearerTokenConverter = new ServerBearerTokenAuthenticationConverter();
		bearerTokenConverter.setAllowUriQueryParameter(true);
		http.oauth2ResourceServer().bearerTokenConverter(bearerTokenConverter);

		// 身份验证入口点
		http.exceptionHandling().authenticationEntryPoint(serverAuthenticationEntryPoint);
		// 服务器访问被拒绝处理程序
		http.oauth2ResourceServer().accessDeniedHandler(serverAccessDeniedHandler);

		return http.build();
	}

	@Override
	public Mono<AuthorizationDecision> check(Mono<Authentication> authentication,
			AuthorizationContext authorizationContext) {

		ServerWebExchange exchange = authorizationContext.getExchange();

		log.info(exchange.getRequest().getURI().toString());

		boolean whiteList = whiteList(exchange);
		if (whiteList) {
			return Mono.just(new AuthorizationDecision(true));
		}

		return authentication.map(requestAuthentication -> {

			// 将当前用户名放入日志中
			MDC.put(Constant.NAME, SecurityUtils.getUserName(requestAuthentication));

			// 已通过认证授权
			if (requestAuthentication.isAuthenticated()) {
				// 放行
				return new AuthorizationDecision(true);
			}
			else {
				// 未通过认证授权
				// 拒绝放行
				return new AuthorizationDecision(false);
			}
		})
				// 无认证授权
				// 拒绝放行
				.defaultIfEmpty(new AuthorizationDecision(false));
	}

	/**
	 * 白名单配置
	 * @param exchange 服务器网络交换
	 * @return 返回匹配结果
	 */
	private boolean whiteList(ServerWebExchange exchange) {

		ServerHttpRequest request = exchange.getRequest();
		HttpMethod method = request.getMethod();
		URI uri = request.getURI();
		String path = uri.getPath();

		if (method.matches(HttpMethod.OPTIONS.name())) {
			log.debug("放行：{}：{}", method, path);
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

		// 放行指定IP访问端点
		// 支持：/actuator/**、/xxx/actuator/**
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
