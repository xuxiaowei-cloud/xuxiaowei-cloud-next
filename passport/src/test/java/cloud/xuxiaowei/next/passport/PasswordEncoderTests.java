package cloud.xuxiaowei.next.passport;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码编辑器 测试类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
class PasswordEncoderTests {

    /**
     * 密码加密
     */
    @Test
    void encode() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encode = passwordEncoder.encode("xuxiaowei_client_secret");
        log.info(encode);
    }

    /**
     * 密码比较
     */
    @Test
    void matches() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encodedPassword = "{bcrypt}$2a$10$s/3CEiHunH9wo2qr7JfeD.SRa8kK2Y8lOriHWrWhidQX3hyhuORlO";
        boolean matches = passwordEncoder.matches("xuxiaowei_client_secret", encodedPassword);
        log.info(String.valueOf(matches));
    }

}
