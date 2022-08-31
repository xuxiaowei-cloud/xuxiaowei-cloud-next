package cloud.xuxiaowei.next.oauth2.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;

/**
 * 配置 Bearer Token
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
public class BearerTokenResolverConfiguration {

	/**
	 * @see OAuth2ResourceServerConfigurer#configure(HttpSecurityBuilder)
	 */
	@Bean
	public BearerTokenResolver bearerTokenResolver() {
		DefaultBearerTokenResolver bearerTokenResolver = new DefaultBearerTokenResolver();
		// 设置是否支持使用 URI 参数传输访问令牌。默认为 {@code false}。
		bearerTokenResolver.setAllowUriQueryParameter(true);
		return bearerTokenResolver;
	}

}
