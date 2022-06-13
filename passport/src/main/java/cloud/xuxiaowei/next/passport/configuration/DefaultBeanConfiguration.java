package cloud.xuxiaowei.next.passport.configuration;

import cloud.xuxiaowei.next.core.properties.CloudSecurityProperties;
import cloud.xuxiaowei.next.passport.service.impl.DefaultCsrfRequestMatcherImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.sql.DataSource;

import static cloud.xuxiaowei.next.passport.service.impl.DefaultCsrfRequestMatcherImpl.CSRF_REQUEST_MATCHER_BEAN_NAME;

/**
 * 默认 {@link Bean} 配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
public class DefaultBeanConfiguration {

    private ServerProperties serverProperties;

    private CloudSecurityProperties cloudSecurityProperties;

    @Autowired
    public void setServerProperties(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Autowired
    public void setCloudSecurityProperties(CloudSecurityProperties cloudSecurityProperties) {
        this.cloudSecurityProperties = cloudSecurityProperties;
    }

    /**
     * @see UserDetailsService 用于检索用户进行身份验证的实例。
     */
    @Bean
    @ConditionalOnMissingBean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    /**
     * 默认 CSRF 请求匹配匹配 {@link Bean} 配置
     *
     * @return 返回 默认 CSRF 请求匹配匹配 {@link Bean}
     */
    @Bean(CSRF_REQUEST_MATCHER_BEAN_NAME)
    @ConditionalOnMissingBean(name = CSRF_REQUEST_MATCHER_BEAN_NAME)
    public RequestMatcher defaultCsrfRequestMatcher() {
        ServerProperties.Servlet servlet = serverProperties.getServlet();
        String contextPath = servlet.getContextPath();
        return new DefaultCsrfRequestMatcherImpl(contextPath, cloudSecurityProperties);
    }

}
