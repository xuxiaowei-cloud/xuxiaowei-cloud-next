package cloud.xuxiaowei.next.passport.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

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

    /**
     * @see UserDetailsService 用于检索用户进行身份验证的实例。
     */
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/favicon.ico");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated());

        http.oauth2Login(oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/authorization-server-oidc")).oauth2Client(withDefaults());

        return http.build();
    }

}
