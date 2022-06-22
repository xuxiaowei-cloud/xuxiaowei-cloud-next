package cloud.xuxiaowei.next.utils;

import lombok.Data;
import org.slf4j.MDC;

import java.io.Serializable;

/**
 * 响应数据
 *
 * @param <T> 泛型
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class Response<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String CODE = "code";

	public static final String MSG = "msg";

	public static final String DATA = "data";

	public static final String FIELD = "field";

	public static final String EXPLAIN = "explain";

	public static final String REQUEST_ID = "requestId";

	/**
	 * 仅为自动装载数据使用
	 */
	private Response() {

	}

	public Response(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Response(String code, String msg, String field) {
		this.code = code;
		this.msg = msg;
		this.field = field;
	}

	public static Response<?> ok() {
		return new Response<>(CodeEnums.OK.code, CodeEnums.OK.msg);
	}

	public static Response<?> ok(String msg) {
		return new Response<>(CodeEnums.OK.code, msg);
	}

	public static <T> Response<?> ok(T data) {
		Response<T> response = new Response<>(CodeEnums.OK.code, CodeEnums.OK.msg);
		response.setData(data);
		return response;
	}

	public static <T> Response<?> ok(T data, String msg) {
		Response<T> response = new Response<>(CodeEnums.OK.code, CodeEnums.OK.msg);
		response.setData(data);
		return response;
	}

	public static Response<?> error() {
		return new Response<>(CodeEnums.ERROR.code, CodeEnums.ERROR.msg);
	}

	public static Response<?> error(String msg) {
		return new Response<>(CodeEnums.ERROR.code, msg);
	}

	public static Response<?> error(String code, String msg) {
		return new Response<>(code, msg);
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
	private T data;

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
