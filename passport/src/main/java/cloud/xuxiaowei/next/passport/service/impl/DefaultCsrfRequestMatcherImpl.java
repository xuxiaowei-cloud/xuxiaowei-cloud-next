package cloud.xuxiaowei.next.passport.service.impl;

import cloud.xuxiaowei.next.core.properties.CloudSecurityProperties;
import cloud.xuxiaowei.next.passport.configuration.WebSecurityConfigurerAdapterConfiguration;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 默认 CSRF 服务接口实现类
 *
 * @author xuxiaowei
 * @see WebSecurityConfigurerAdapterConfiguration#setCsrfRequestMatcher(RequestMatcher)
 * @see CsrfFilter#DEFAULT_CSRF_MATCHER
 * @since 0.0.1
 */
public class DefaultCsrfRequestMatcherImpl implements RequestMatcher {

    public static final String CSRF_REQUEST_MATCHER_BEAN_NAME = "csrfRequestMatcher";

    private final String contextPath;

    private final CloudSecurityProperties cloudSecurityProperties;

    public DefaultCsrfRequestMatcherImpl(String contextPath, CloudSecurityProperties cloudSecurityProperties) {
        this.contextPath = contextPath == null ? "" : contextPath;
        this.cloudSecurityProperties = cloudSecurityProperties;
    }

    @Override
    public boolean matches(HttpServletRequest request) {

        // "GET", "HEAD", "TRACE", "OPTIONS" 是否验证
        boolean defaultValid = CsrfFilter.DEFAULT_CSRF_MATCHER.matches(request);
        if (!defaultValid) {
            return false;
        }

        // CSRF 是否开启
        boolean csrfEnabled = cloudSecurityProperties.isCsrfEnabled();
        if (csrfEnabled) {

            LinkedHashMap<String, List<HttpMethod>> csrfDisableUrl = cloudSecurityProperties.getCsrfDisableUrl();

            if (csrfDisableUrl == null) {
                // 禁用的 CSRF 地址为空，即：所有地址均要验证 CSRF Token
                return true;
            }

            // 当前访问的 URL 地址
            String requestUri = contextPath + request.getRequestURI();

            // 获取 HTTP 请求方法
            HttpMethod resolve = HttpMethod.valueOf(request.getMethod());

            AntPathMatcher antPathMatcher = new AntPathMatcher();

            for (String disableUrl : csrfDisableUrl.keySet()) {

                boolean match = antPathMatcher.match(disableUrl, requestUri);

                // 此 URL 地址 禁用的 HTTP 方法
                List<HttpMethod> httpMethods = csrfDisableUrl.get(disableUrl);

                if (match && httpMethods.contains(resolve)) {
                    // 当前访问的 URL 已禁用 CSRF
                    return false;
                }

            }

        } else {
            return false;
        }

        // 未完全匹配（URL 与 HTTP 方法同时匹配）的地址，均需要验证 CSRF
        return true;
    }

}
