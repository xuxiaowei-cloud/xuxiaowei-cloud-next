package cloud.xuxiaowei.next.user.configuration;

import cloud.xuxiaowei.next.core.properties.JwkKeyProperties;
import cloud.xuxiaowei.next.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
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
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        // 表单登录处理从授权服务器过滤器链到登录页面的重定向
        http.authorizeHttpRequests((authorize) -> {
            authorize
                    // 端点放行
                    .antMatchers("/" + Constant.ACTUATOR + "/**").permitAll()
                    // 注销登录
                    .antMatchers("/signout").permitAll()
                    // 其他路径均需要授权
                    .anyRequest().authenticated();
        }).formLogin(Customizer.withDefaults());

        http.oauth2ResourceServer().jwt(oauth2ResourceServer -> {
            RSAPublicKey rsaPublicKey = jwkKeyProperties.rsaPublicKey();
            NimbusJwtDecoder.PublicKeyJwtDecoderBuilder publicKeyJwtDecoderBuilder = NimbusJwtDecoder.withPublicKey(rsaPublicKey);
            NimbusJwtDecoder nimbusJwtDecoder = publicKeyJwtDecoderBuilder.build();
            oauth2ResourceServer.decoder(nimbusJwtDecoder);
        });

        return http.build();
    }

}
