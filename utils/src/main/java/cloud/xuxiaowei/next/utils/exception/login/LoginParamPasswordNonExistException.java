package cloud.xuxiaowei.next.utils.exception.login;

import cloud.xuxiaowei.next.utils.CodeEnums;

/**
 * 登录参数密码不存在 异常
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class LoginParamPasswordNonExistException extends LoginParamNonExistException {

	public LoginParamPasswordNonExistException() {
		super(CodeEnums.A10003.code, CodeEnums.A10003.msg);
	}

	public LoginParamPasswordNonExistException(String msg) {
		super(CodeEnums.A10003.code, msg);
	}

	public LoginParamPasswordNonExistException(String code, String msg) {
		super(code, msg);
	}

	public LoginParamPasswordNonExistException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	public LoginParamPasswordNonExistException(String code, String msg, String field, Throwable cause) {
		super(code, msg, field, cause);
	}

	public LoginParamPasswordNonExistException(String code, String msg, String field, String explain, Throwable cause) {
		super(code, msg, field, explain, cause);
	}

	public LoginParamPasswordNonExistException(Throwable cause) {
		super(CodeEnums.A10003.msg, cause);
		this.code = CodeEnums.A10003.code;
		this.msg = CodeEnums.A10003.msg;
	}

	public LoginParamPasswordNonExistException(String msg, Throwable cause) {
		super(msg, cause);
		this.code = CodeEnums.A10003.code;
		this.msg = msg;
	}

}
