package cloud.xuxiaowei.next.passport.handler;

import cloud.xuxiaowei.next.core.properties.CloudSecurityProperties;
import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.SecurityUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * 身份验证成功处理程序
 *
 * @author xuxiaowei
 * @see ForwardAuthenticationSuccessHandler
 * @since 0.0.1
 */
@Slf4j
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private CloudSecurityProperties cloudSecurityProperties;

    @Autowired
    public void setCloudSecurityProperties(CloudSecurityProperties cloudSecurityProperties) {
        this.cloudSecurityProperties = cloudSecurityProperties;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String userName = SecurityUtils.getUserName(authentication);

        MDC.put(Constant.NAME, userName);

        String successForwardUrl = cloudSecurityProperties.getSuccessForwardUrl();
        Assert.isTrue(UrlUtils.isValidRedirectUrl(successForwardUrl), () -> "'" + successForwardUrl + "' 不是有效的转发URL");

        request.getRequestDispatcher(successForwardUrl).forward(request, response);

    }

}
