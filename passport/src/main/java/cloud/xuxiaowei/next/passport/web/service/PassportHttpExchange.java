package cloud.xuxiaowei.next.passport.web.service;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * Passport 服务
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@HttpExchange("http://passport")
public interface PassportHttpExchange {

	/**
	 * OAuth 2.1 Token 接口
	 * @return 返回 OAuth 2.1 Token
	 */
	@GetExchange("/oauth2/token")
	String oauth2Token();

}
