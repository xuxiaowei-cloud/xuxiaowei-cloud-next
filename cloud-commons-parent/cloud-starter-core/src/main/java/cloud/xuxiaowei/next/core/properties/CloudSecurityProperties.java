package cloud.xuxiaowei.next.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 微服务 Security 配置
 * <p>
 * 运行时刷新
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties("cloud.security")
public class CloudSecurityProperties {

    /**
     * 默认登录页面地址
     *
     * @see DefaultLoginPageGeneratingFilter#DEFAULT_LOGIN_PAGE_URL
     */
    private String defaultLoginPageUrl = "http://passport.example.next.xuxiaowei.cloud:1411";

    /**
     * 登录页面地址
     *
     * @see FormLoginConfigurer#loginPage(String)
     */
    private String loginPageUrl = "/";

    /**
     * 登录请求地址
     * <p>
     * 验证用户名和密码的 URL
     *
     * @see FormLoginConfigurer#loginProcessingUrl(String)
     */
    private String loginProcessingUrl = "/login";

    /**
     * 登录成功地址
     *
     * @see FormLoginConfigurer#successForwardUrl(String)
     */
    private String successForwardUrl = "/login/success";

    /**
     * 是否开启 CSRF
     */
    private boolean csrfEnabled = true;

    /**
     * 禁用 CSRF URL与 HTTP 方法
     * <p>
     * 1、URL 支持通配符<p>
     * 2、存在设置中的优先级（越靠前，优先级越高）
     */
    private LinkedHashMap<String, List<HttpMethod>> csrfDisableUrl;

    /**
     * 是否开启RSA密码解密
     */
    private boolean enabledRsa = false;

    /**
     * 是否允许空权限登录
     */
    private boolean allowEmptyAuthorities = true;

}
