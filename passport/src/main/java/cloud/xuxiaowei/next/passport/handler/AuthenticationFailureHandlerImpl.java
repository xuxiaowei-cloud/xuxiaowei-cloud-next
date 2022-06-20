package cloud.xuxiaowei.next.passport.handler;

import cloud.xuxiaowei.next.utils.CodeEnums;
import cloud.xuxiaowei.next.utils.Response;
import cloud.xuxiaowei.next.utils.ResponseUtils;
import cloud.xuxiaowei.next.utils.exception.login.LoginException;
import cloud.xuxiaowei.next.utils.exception.login.LoginParamPasswordValidException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 身份验证失败处理程序
 *
 * @author xuxiaowei
 * @see FormLoginConfigurer#failureUrl(String)
 * @see WebAttributes#AUTHENTICATION_EXCEPTION
 * @see WebAttributes#ACCESS_DENIED_403
 * @see WebAttributes#WEB_INVOCATION_PRIVILEGE_EVALUATOR_ATTRIBUTE
 * @since 0.0.1
 */
@Slf4j
@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        Response<?> error;

        if (exception instanceof DisabledException) {
            error = Response.error(CodeEnums.A30001.code, CodeEnums.A30001.msg);
        } else if (exception instanceof AccountExpiredException) {
            error = Response.error(CodeEnums.A30002.code, CodeEnums.A30002.msg);
        } else if (exception instanceof CredentialsExpiredException) {
            error = Response.error(CodeEnums.A30003.code, CodeEnums.A30003.msg);
        } else if (exception instanceof LockedException) {
            error = Response.error(CodeEnums.A30004.code, CodeEnums.A30004.msg);
        } else if (exception instanceof LoginParamPasswordValidException passwordValidException) {
            error = Response.error(passwordValidException.getCode(), passwordValidException.getMsg());
        } else if (exception instanceof InternalAuthenticationServiceException internalAuthenticationServiceException) {
            Throwable cause = internalAuthenticationServiceException.getCause();
            if (cause instanceof LoginException loginException) {
                error = Response.error(loginException.getCode(), loginException.getMsg());
            } else {
                error = Response.error(CodeEnums.A10000.code, "内部认证服务异常");
                error.setExplain("异常代码待划分");
            }
        } else {
            // 此处可增加其他异常的判断
            error = Response.error(CodeEnums.A10000.code, CodeEnums.A10000.msg);
        }

        log.error(error.getMsg(), exception);

        ResponseUtils.response(response, error);
    }

}
