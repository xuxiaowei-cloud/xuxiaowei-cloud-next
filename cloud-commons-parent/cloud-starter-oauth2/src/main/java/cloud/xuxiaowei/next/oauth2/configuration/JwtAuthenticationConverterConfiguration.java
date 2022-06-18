package cloud.xuxiaowei.next.oauth2.configuration;

import cloud.xuxiaowei.next.utils.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

/**
 * 支持 {@link EnableGlobalMethodSecurity}、{@link PreAuthorize}
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JwtAuthenticationConverterConfiguration {

    /**
     * 支持 {@link EnableGlobalMethodSecurity}、{@link PreAuthorize} 的 {@link Bean}
     *
     * @return 支持 {@link EnableGlobalMethodSecurity}、{@link PreAuthorize}
     * @see <a href="https://docs.spring.io/spring-security/reference/6.0.0-M3/servlet/oauth2/resource-server/jwt.html#oauth2resourceserver-jwt-authorization-extraction">手动提取权限-6.0.0-M3</a>
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // 设置Token中获取权限数据的声明名称
        grantedAuthoritiesConverter.setAuthoritiesClaimName(Constant.AUTHORITIES);
        // 设置成无前缀
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

}
