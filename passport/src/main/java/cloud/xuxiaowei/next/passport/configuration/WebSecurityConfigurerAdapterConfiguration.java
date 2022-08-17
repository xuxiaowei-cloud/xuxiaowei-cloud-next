package cloud.xuxiaowei.next.passport.configuration;

import cloud.xuxiaowei.next.core.properties.CloudRememberMeProperties;
import cloud.xuxiaowei.next.core.properties.CloudSecurityProperties;
import cloud.xuxiaowei.next.core.properties.CloudJwkKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.security.interfaces.RSAPublicKey;

import static cloud.xuxiaowei.next.oauth2.impl.CsrfRequestMatcherImpl.CSRF_REQUEST_MATCHER_BEAN_NAME;

/**
 * Spring Security 配置
 *
 * @author xuxiaowei
 * @see UsernamePasswordAuthenticationFilter 用户名密码认证过滤器
 * @see DefaultLoginPageGeneratingFilter 默认登录页面
 * @see HttpSecurity#formLogin() 登录配置
 * @since 0.0.1
 */
@Configuration
public class WebSecurityConfigurerAdapterConfiguration {

	private CloudJwkKeyProperties cloudJwkKeyProperties;

	private AccessDeniedHandler accessDeniedHandler;

	private AuthenticationEntryPoint authenticationEntryPoint;

	private UserDetailsService userDetailsService;

	private CloudSecurityProperties cloudSecurityProperties;

	private CloudRememberMeProperties cloudRememberMeProperties;

	private RequestMatcher csrfRequestMatcher;

	private AuthenticationSuccessHandler authenticationSuccessHandler;

	private AuthenticationFailureHandler authenticationFailureHandler;

	@Autowired
	public void setJwkKeyProperties(CloudJwkKeyProperties cloudJwkKeyProperties) {
		this.cloudJwkKeyProperties = cloudJwkKeyProperties;
	}

	@Autowired
	public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
		this.accessDeniedHandler = accessDeniedHandler;
	}

	@Autowired
	public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Autowired
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Autowired
	public void setCloudSecurityProperties(CloudSecurityProperties cloudSecurityProperties) {
		this.cloudSecurityProperties = cloudSecurityProperties;
	}

	@Autowired
	public void setCloudRememberMeProperties(CloudRememberMeProperties cloudRememberMeProperties) {
		this.cloudRememberMeProperties = cloudRememberMeProperties;
	}

	@Autowired
	@Qualifier(CSRF_REQUEST_MATCHER_BEAN_NAME)
	public void setCsrfRequestMatcher(RequestMatcher csrfRequestMatcher) {
		this.csrfRequestMatcher = csrfRequestMatcher;
	}

	@Autowired
	public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
		this.authenticationSuccessHandler = authenticationSuccessHandler;
	}

	@Autowired
	@Qualifier("authenticationFailureHandlerImpl")
	public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationFailureHandler = authenticationFailureHandler;
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> {
			web.ignoring().antMatchers("/favicon.ico");
			web.ignoring().antMatchers("/**/**.js");
			web.ignoring().antMatchers("/**/**.css");
		};
	}

	/**
	 * @see <a href=
	 * "https://docs.spring.io/spring-security/reference/servlet/authentication/index.html">用于身份验证</a>
	 * 的 Spring Security 过滤器链。
	 */
	@Bean
	@Order(0)
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// 资源服务配置秘钥
		http.oauth2ResourceServer().jwt(oauth2ResourceServer -> {
			RSAPublicKey rsaPublicKey = cloudJwkKeyProperties.rsaPublicKey();
			NimbusJwtDecoder.PublicKeyJwtDecoderBuilder publicKeyJwtDecoderBuilder = NimbusJwtDecoder
					.withPublicKey(rsaPublicKey);
			NimbusJwtDecoder nimbusJwtDecoder = publicKeyJwtDecoderBuilder.build();
			oauth2ResourceServer.decoder(nimbusJwtDecoder);
		});

		// 异常处理
		http.exceptionHandling(exceptionHandlingCustomizer -> {
			exceptionHandlingCustomizer
					// 访问被拒绝处理程序
					.accessDeniedHandler(accessDeniedHandler)
					// 身份验证入口点
					.authenticationEntryPoint(authenticationEntryPoint);
		});

		// 用于检索用户进行身份验证的实例
		http.userDetailsService(userDetailsService);

		// 路径权限控制
		http.authorizeHttpRequests((authorize) -> {
			authorize
					// 放行端点
					.antMatchers("/actuator/**").permitAll()
					// 放行授权路径
					.antMatchers("/oauth2/authorize").permitAll()
					// 放行检查Token
					.antMatchers("/oauth2/check_token").permitAll()
					// 放行Token
					.antMatchers("/oauth2/token").permitAll()
					// 注销登录放行
					.antMatchers("/signout").permitAll()
					// 放行错误地址
					.antMatchers("/error").permitAll()
					// 其他路径均需要授权
					.anyRequest().authenticated();
		});

		http.formLogin(formLogin -> formLogin
				// 登录页面地址
				.loginPage(cloudSecurityProperties.getLoginPageUrl())
				// 登录请求地址
				.loginProcessingUrl(cloudSecurityProperties.getLoginProcessingUrl())
				// 身份验证失败处理程序
				.failureHandler(authenticationFailureHandler)
				// 登录成功后的处理，重定向到某个地址
				.successHandler(authenticationSuccessHandler)
				// 已上地址，允许任何人访问
				.permitAll());

		http.rememberMe(rememberMe -> rememberMe
				// 查询用户
				.userDetailsService(userDetailsService)
				// 记住我参数名
				.rememberMeParameter(cloudRememberMeProperties.getRememberMeParameter())
				// 记住我 Cookie 名
				.rememberMeCookieName(cloudRememberMeProperties.getRememberMeCookieName())
				// 记住我域名
				.rememberMeCookieDomain(cloudRememberMeProperties.getRememberMeCookieDomain())
				// 秘钥
				.key(cloudRememberMeProperties.getKey())
				// 记住我 Token 有效时间
				.tokenValiditySeconds(cloudRememberMeProperties.getTokenValiditySeconds()));

		// CSRF 配置
		http.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher);

		return http.build();
	}

}
