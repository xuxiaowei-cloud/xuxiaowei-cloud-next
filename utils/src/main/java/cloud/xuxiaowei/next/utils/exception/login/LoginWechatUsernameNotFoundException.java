package cloud.xuxiaowei.next.utils.exception.login;

import cloud.xuxiaowei.next.utils.CodeEnums;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 登录 微信用户名没有找到 异常
 *
 * @author xuxiaowei
 * @see UsernameNotFoundException
 * @since 0.0.1
 */
public class LoginWechatUsernameNotFoundException extends LoginUsernameNotFoundException {

	public LoginWechatUsernameNotFoundException() {
		super(CodeEnums.A20002.code, CodeEnums.A20002.msg);
	}

	public LoginWechatUsernameNotFoundException(String msg) {
		super(CodeEnums.A20002.code, msg);
	}

	public LoginWechatUsernameNotFoundException(String code, String msg) {
		super(code, msg);
	}

	public LoginWechatUsernameNotFoundException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	public LoginWechatUsernameNotFoundException(String code, String msg, String field, Throwable cause) {
		super(code, msg, field, cause);
	}

	public LoginWechatUsernameNotFoundException(String code, String msg, String field, String explain,
			Throwable cause) {
		super(code, msg, field, explain, cause);
	}

	public LoginWechatUsernameNotFoundException(Throwable cause) {
		super(CodeEnums.A20002.code, CodeEnums.A20002.code, cause);
	}

	public LoginWechatUsernameNotFoundException(String msg, Throwable cause) {
		super(CodeEnums.A20002.code, msg, cause);
	}

}
