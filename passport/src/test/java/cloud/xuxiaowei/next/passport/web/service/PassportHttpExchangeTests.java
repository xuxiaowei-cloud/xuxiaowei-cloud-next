package cloud.xuxiaowei.next.passport.web.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Passport 服务 测试类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@SpringBootTest
class PassportHttpExchangeTests {

	@Autowired
	private PassportHttpExchange passportHttpExchange;

	@Test
	void oauth2Token() {
		String token = passportHttpExchange.oauth2Token();
		log.info(token);
	}

}
