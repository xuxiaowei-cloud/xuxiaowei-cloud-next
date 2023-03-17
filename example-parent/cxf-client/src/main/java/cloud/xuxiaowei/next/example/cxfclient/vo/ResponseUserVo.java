package cloud.xuxiaowei.next.example.cxfclient.vo;

import cloud.xuxiaowei.utils.CodeEnums;
import cloud.xuxiaowei.utils.Constant;
import lombok.Data;
import org.slf4j.MDC;

import java.io.Serializable;

/**
 * 用户响应
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class ResponseUserVo implements Serializable {

	private static final long serialVersionUID = 1L;

	public ResponseUserVo() {

	}

	public ResponseUserVo(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ResponseUserVo(String code, String msg, String field) {
		this.code = code;
		this.msg = msg;
		this.field = field;
	}

	public static ResponseUserVo ok() {
		return new ResponseUserVo(CodeEnums.OK.code, CodeEnums.OK.msg);
	}

	public static ResponseUserVo ok(String msg) {
		return new ResponseUserVo(CodeEnums.OK.code, msg);
	}

	public static ResponseUserVo ok(UserVo data) {
		ResponseUserVo responseUserVo = new ResponseUserVo(CodeEnums.OK.code, CodeEnums.OK.msg);
		responseUserVo.setData(data);
		return responseUserVo;
	}

	public static ResponseUserVo ok(UserVo data, String msg) {
		ResponseUserVo responseUserVo = new ResponseUserVo(CodeEnums.OK.code, CodeEnums.OK.msg);
		responseUserVo.setData(data);
		return responseUserVo;
	}

	public static ResponseUserVo error() {
		return new ResponseUserVo(CodeEnums.ERROR.code, CodeEnums.ERROR.msg);
	}

	public static ResponseUserVo error(String msg) {
		return new ResponseUserVo(CodeEnums.ERROR.code, msg);
	}

	public static ResponseUserVo error(String code, String msg) {
		return new ResponseUserVo(code, msg);
	}

	/**
	 * 响应代码
	 *
	 * @see CodeEnums#code
	 */
	private String code;

	/**
	 * 响应消息
	 *
	 * @see CodeEnums#msg
	 */
	private String msg;

	/**
	 * 返回数据
	 */
	private UserVo data;

	/**
	 * 错误字段
	 * <p>
	 * 存在多个时，使用英文逗号隔开
	 */
	private String field;

	/**
	 * 说明
	 */
	private String explain;

	/**
	 * 请求ID
	 */
	private String requestId;

	/**
	 * 获取 请求ID
	 * @return 在请求ID 为 null，返回 MDC 中的 请求ID
	 */
	public String getRequestId() {
		if (this.requestId == null) {
			return MDC.get(Constant.REQUEST_ID);
		}
		return this.requestId;
	}

}
