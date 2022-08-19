package cloud.xuxiaowei.next.utils.exception.token;

import cloud.xuxiaowei.next.utils.CodeEnums;

/**
 * Token 过期 异常
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class TokenExpiredException extends TokenException {

	public TokenExpiredException() {
		super(CodeEnums.T10002.code, CodeEnums.T10002.msg);
	}

	public TokenExpiredException(String msg) {
		super(CodeEnums.T10002.code, msg);
	}

	public TokenExpiredException(String code, String msg) {
		super(code, msg);
	}

	public TokenExpiredException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
	}

	public TokenExpiredException(String code, String msg, String field) {
		super(msg);
		this.code = code;
		this.msg = msg;
		this.field = field;
	}

	public TokenExpiredException(String code, String msg, String field, Throwable cause) {
		super(code, msg, field, cause);
	}

	public TokenExpiredException(String code, String msg, String field, String explain, Throwable cause) {
		super(code, msg, field, explain, cause);
	}

	public TokenExpiredException(Throwable cause) {
		super(cause);
	}

	public TokenExpiredException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
