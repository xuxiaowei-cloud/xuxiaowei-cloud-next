package cloud.xuxiaowei.next.utils.exception;

import cloud.xuxiaowei.next.utils.CodeEnums;
import cloud.xuxiaowei.next.utils.exception.login.LoginException;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

/**
 * 微服务 异常父类
 * <p>
 * 注意：
 * <p>
 * 1、{@link LoginException} 及其子异常不属于此异常的子类
 * <p>
 * 1、ClientException 及其子异常不属于此异常的子类
 * <p>
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CloudException extends Exception {

	/**
	 * 错误码
	 */
	@Setter(AccessLevel.NONE)
	protected String code;

	/**
	 * 中文描述
	 */
	@Setter(AccessLevel.NONE)
	protected String msg;

	/**
	 * 错误字段
	 * <p>
	 * 存在多个时，使用英文逗号隔开
	 */
	@Setter(AccessLevel.NONE)
	protected String field;

	/**
	 * 说明
	 */
	@Setter(AccessLevel.NONE)
	protected String explain;

	public CloudException() {
		super(CodeEnums.ERROR.msg);
		this.code = CodeEnums.ERROR.code;
		this.msg = CodeEnums.ERROR.msg;
	}

	public CloudException(String msg) {
		super(msg);
		this.code = CodeEnums.ERROR.code;
		this.msg = msg;
	}

	public CloudException(String code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public CloudException(String code, String msg, Throwable cause) {
		super(msg, cause);
		this.code = code;
		this.msg = msg;
	}

	public CloudException(String code, String msg, String field) {
		super(msg);
		this.code = code;
		this.msg = msg;
		this.field = field;
	}

	public CloudException(String code, String msg, String field, Throwable cause) {
		super(msg, cause);
		this.code = code;
		this.msg = msg;
		this.field = field;
	}

	public CloudException(String code, String msg, String field, String explain, Throwable cause) {
		super(msg, cause);
		this.code = code;
		this.msg = msg;
		this.field = field;
		this.explain = explain;
	}

	public CloudException(Throwable cause) {
		super(CodeEnums.ERROR.msg, cause);
		this.code = CodeEnums.ERROR.code;
		this.msg = CodeEnums.ERROR.msg;
	}

	public CloudException(String msg, Throwable cause) {
		super(msg, cause);
		this.code = CodeEnums.ERROR.code;
		this.msg = msg;
	}

}
