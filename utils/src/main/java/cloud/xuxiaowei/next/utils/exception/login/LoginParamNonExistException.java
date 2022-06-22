package cloud.xuxiaowei.next.utils.exception.login;

import cloud.xuxiaowei.next.utils.CodeEnums;

/**
 * 登录参数不存在 异常父类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class LoginParamNonExistException extends LoginException {

	public LoginParamNonExistException() {
		super(CodeEnums.A10001.code, CodeEnums.A10001.msg);
	}

	public LoginParamNonExistException(String msg) {
		super(CodeEnums.A10001.code, msg);
	}

	public LoginParamNonExistException(String code, String msg) {
		super(code, msg);
	}

	public LoginParamNonExistException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	public LoginParamNonExistException(String code, String msg, String field, Throwable cause) {
		super(code, msg, field, cause);
	}

	public LoginParamNonExistException(String code, String msg, String field, String explain, Throwable cause) {
		super(code, msg, field, explain, cause);
	}

	public LoginParamNonExistException(Throwable cause) {
		super(CodeEnums.A10001.msg, cause);
		this.code = CodeEnums.A10001.code;
		this.msg = CodeEnums.A10001.msg;
	}

	public LoginParamNonExistException(String msg, Throwable cause) {
		super(msg, cause);
		this.code = CodeEnums.A10001.code;
		this.msg = msg;
	}

}
