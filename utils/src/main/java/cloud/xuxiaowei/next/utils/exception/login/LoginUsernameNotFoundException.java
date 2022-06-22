package cloud.xuxiaowei.next.utils.exception.login;

import cloud.xuxiaowei.next.utils.CodeEnums;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 登录 用户名没有找到 异常
 *
 * @author xuxiaowei
 * @see UsernameNotFoundException
 * @since 0.0.1
 */
public class LoginUsernameNotFoundException extends LoginException {

	public LoginUsernameNotFoundException() {
		super(CodeEnums.A20002.code, CodeEnums.A20002.msg);
	}

	public LoginUsernameNotFoundException(String msg) {
		super(CodeEnums.A20002.code, msg);
	}

	public LoginUsernameNotFoundException(String code, String msg) {
		super(code, msg);
	}

	public LoginUsernameNotFoundException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	public LoginUsernameNotFoundException(String code, String msg, String field, Throwable cause) {
		super(code, msg, field, cause);
	}

	public LoginUsernameNotFoundException(String code, String msg, String field, String explain, Throwable cause) {
		super(code, msg, field, explain, cause);
	}

	public LoginUsernameNotFoundException(Throwable cause) {
		super(CodeEnums.A20002.code, CodeEnums.A20002.code, cause);
	}

	public LoginUsernameNotFoundException(String msg, Throwable cause) {
		super(CodeEnums.A20002.code, msg, cause);
	}

}
