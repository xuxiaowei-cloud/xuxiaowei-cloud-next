package cloud.xuxiaowei.next.passport.service.impl;

import cloud.xuxiaowei.next.core.properties.CloudSecurityProperties;
import cloud.xuxiaowei.next.passport.controller.IndexController;
import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.exception.login.LoginParamPasswordNonExistException;
import cloud.xuxiaowei.next.utils.exception.login.LoginParamPasswordValidException;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 默认密码编辑器
 *
 * @author xuxiaowei
 * @see PasswordEncoderFactories#createDelegatingPasswordEncoder()
 * @since 0.0.1
 */
@Slf4j
@Component
public class DefaultPasswordEncoderImpl implements PasswordEncoder {

    private HttpServletRequest request;

    private CloudSecurityProperties cloudSecurityProperties;

    @Autowired
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Autowired
    public void setCloudSecurityProperties(CloudSecurityProperties cloudSecurityProperties) {
        this.cloudSecurityProperties = cloudSecurityProperties;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        if (StringUtils.hasText(rawPassword)) {
            return rawPassword.toString();
        } else {
            LoginParamPasswordNonExistException loginException = new LoginParamPasswordNonExistException("登录参数不存在密码");
            loginException.setUsername(request.getParameter(Constant.USERNAME));
            throw loginException;
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        HttpSession session = request.getSession();
        String rsaPrivateKeyBase64 = session.getAttribute(IndexController.RSA_PRIVATE_KEY_BASE64) + "";

        boolean matches;
        try {
            if (cloudSecurityProperties.isEnabledRsa()) {
                log.info("比较密码时启用了RSA对密码进行解密");
                RSA rsa = new RSA(rsaPrivateKeyBase64, null);
                String rawPasswordDecryptStr = rsa.decryptStr(rawPassword.toString(), KeyType.PrivateKey);

                matches = passwordEncoder.matches(rawPasswordDecryptStr, encodedPassword);
            } else {
                log.info("比较密码时未启用RSA对密码进行解密");
                matches = passwordEncoder.matches(rawPassword, encodedPassword);
            }
        } catch (Exception e) {
            // 可能根据用户名没有找到用户信息（即：密码），导致比较失败
            LoginParamPasswordValidException loginException = new LoginParamPasswordValidException("比较密码时异常", e);
            loginException.setUsername(request.getParameter(Constant.USERNAME));
            throw loginException;
        }
        if (!matches) {
            LoginParamPasswordValidException loginException = new LoginParamPasswordValidException("密码不匹配");
            loginException.setUsername(request.getParameter(Constant.USERNAME));
            throw loginException;
        }

        return true;
    }

}
