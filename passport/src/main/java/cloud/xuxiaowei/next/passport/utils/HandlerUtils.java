package cloud.xuxiaowei.next.passport.utils;

import cloud.xuxiaowei.next.passport.entity.UsersLogin;
import cloud.xuxiaowei.next.system.entity.Users;
import cloud.xuxiaowei.next.system.service.IUsersService;
import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.DateUtils;
import cloud.xuxiaowei.next.utils.RequestUtils;
import cloud.xuxiaowei.next.utils.exception.ExceptionUtils;
import cloud.xuxiaowei.next.utils.exception.login.LoginException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

import static org.springframework.security.web.WebAttributes.AUTHENTICATION_EXCEPTION;

/**
 * 处理程序 工具类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
public class HandlerUtils {

    /**
     * 登录成功、失败 处理程序 要保存的数据
     *
     * @param username  用户名
     * @param success   登录结果
     * @param request   请求
     * @param exception 异常
     * @return 返回 要保存的数据
     */
    public static UsersLogin usersLogin(String username, boolean success, HttpServletRequest request, AuthenticationException exception) {
        String method = request.getMethod();
        String remoteHost = request.getRemoteHost();
        String queryString = request.getQueryString();
        String headersMap = RequestUtils.getHeadersJson(request);
        String userAgent = RequestUtils.getUserAgent(request);
        String requestId = MDC.get(Constant.REQUEST_ID);
        HttpSession session = request.getSession(false);

        String sessionId;
        if (session == null) {
            sessionId = null;
        } else {

            sessionId = session.getId();

            // Session 创建时间
            long creationTime = session.getCreationTime();
            // 最后一次访问时间
            long lastAccessedTime = session.getLastAccessedTime();
            // 最大非活动时间
            int maxInactiveInterval = session.getMaxInactiveInterval();

            // 在此可以统计一下登录失败的用户信息（需要将登录信息，如：用户名，放入 异常 中）
            if (exception instanceof LoginException) {
                session.removeAttribute(AUTHENTICATION_EXCEPTION);
            }
        }

        String stackTrace = exception == null ? null : ExceptionUtils.getStackTrace(exception);

        UsersLogin usersLogin = new UsersLogin();
        usersLogin.setUsername(username);
        usersLogin.setSuccess(success);
        usersLogin.setMethod(method);
        usersLogin.setQueryString(queryString);
        usersLogin.setHeadersMap(headersMap);
        usersLogin.setUserAgent(userAgent);
        usersLogin.setRequestId(requestId);
        usersLogin.setSessionId(sessionId);
        usersLogin.setIp(remoteHost);
        usersLogin.setException(stackTrace);

        return usersLogin;
    }

    /**
     * 发送邮件
     *
     * @param usersService   用户 接口服务
     * @param javaMailSender Java 邮件发送者
     * @param mailProperties 邮件配置
     * @param request        请求
     * @param userName       用户名
     * @param subject        邮件主题
     * @param result         登录结果
     */
    public static void send(IUsersService usersService, JavaMailSender javaMailSender, MailProperties mailProperties, HttpServletRequest request, String userName, String subject, String result) {
        Users users = usersService.getByUsername(userName);
        if (users == null) {
            log.error("错误：用户：{} 登录{}后，未找到用户信息，不可发送邮件！！！", userName, result);
            return;
        }

        String email = users.getEmail();
        if (!StringUtils.hasText(email)) {
            log.warn("警号：用户：{} 无邮箱，不可发送邮件！！！", userName);
        }

        if (javaMailSender == null) {
            log.error("错误：邮箱：{} 未登录{}，不可发送邮件！！！", mailProperties.getUsername(), result);
            return;
        }

        String remoteHost = request.getRemoteHost();
        String userAgent = RequestUtils.getUserAgent(request);

        String now = DateUtils.format(LocalDateTime.now(), DateUtils.DEFAULT_DATE_TIME_FORMAT);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailProperties.getUsername());
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(String.format("时间：%s\n用户名：%s\nIP：%s\nUser-Agent：%s\n登录结果：%s", now, userName, remoteHost, userAgent, subject));
        javaMailSender.send(simpleMailMessage);
    }

}
