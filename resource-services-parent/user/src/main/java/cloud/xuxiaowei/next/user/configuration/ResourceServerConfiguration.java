package cloud.xuxiaowei.next.user.configuration;

import cloud.xuxiaowei.next.core.properties.JwkKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPublicKey;

/**
 * 资源服务配置
 *
 * @author xuxiaowei
 * @see <a href="http://127.0.0.1:1701/sns/userinfo?access_token=">访问资源</a>
 * @see <a href="https://docs.spring.io/spring-security/reference/6.0.0-M3/servlet/oauth2/resource-server/jwt.html#oauth2resourceserver-jwt-authorization-extraction">手动提取权限-6.0.0-M3</a>
 * @since 0.0.1
 */
@Configuration
public class ResourceServerConfiguration {

    private JwkKeyProperties jwkKeyProperties;

    @Autowired
    public void setJwkKeyProperties(JwkKeyProperties jwkKeyProperties) {
        this.jwkKeyProperties = jwkKeyProperties;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/favicon.ico");
    }

    /**
     * @see <a href="https://docs.spring.io/spring-security/reference/servlet/authentication/index.html">用于身份验证</a> 的 Spring Security 过滤器链。
     */
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        // 路径权限控制
        http.authorizeHttpRequests((authorize) -> {
            authorize
                    // 放行端点
                    .antMatchers("/actuator/**").permitAll()
                    // 注销登录放行
                    .antMatchers("/signout").permitAll()
                    // 其他路径均需要授权
                    .anyRequest().authenticated();
        });

        // 禁用 form 登录
        http.formLogin().disable();

        // 资源服务配置秘钥
        http.oauth2ResourceServer().jwt(oauth2ResourceServer -> {
            RSAPublicKey rsaPublicKey = jwkKeyProperties.rsaPublicKey();
            NimbusJwtDecoder.PublicKeyJwtDecoderBuilder publicKeyJwtDecoderBuilder = NimbusJwtDecoder.withPublicKey(rsaPublicKey);
            NimbusJwtDecoder nimbusJwtDecoder = publicKeyJwtDecoderBuilder.build();
            oauth2ResourceServer.decoder(nimbusJwtDecoder);
        });

        return http.build();
    }

}
