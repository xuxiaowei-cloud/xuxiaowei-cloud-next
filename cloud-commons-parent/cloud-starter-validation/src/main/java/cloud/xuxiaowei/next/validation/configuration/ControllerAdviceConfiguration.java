package cloud.xuxiaowei.next.validation.configuration;

import cloud.xuxiaowei.next.utils.CodeEnums;
import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.Response;
import cloud.xuxiaowei.next.utils.exception.CloudException;
import cloud.xuxiaowei.next.utils.exception.CloudRuntimeException;
import cloud.xuxiaowei.next.utils.map.ResponseMap;
import cloud.xuxiaowei.next.validation.utils.FieldErrorUtils;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * {@link Controller}、{@link RestController} 异常处理
 * <p>
 * 由于 {@link ControllerAdvice} 不支持通配符，故本配置直接扫描所有 {@link Controller}、{@link RestController}
 * 父包
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@ControllerAdvice({
// @formatter:off
	"cloud.xuxiaowei.next",
	"org.springframework.security.oauth2.provider.endpoint",
	"org.springframework.security.oauth2.provider.request"
// @formatter:on
})
public class ControllerAdviceConfiguration {

	/**
	 * 缺少必需的请求正文
	 */
	private final static String REQUIRED_REQUEST_BODY_IS_MISSING = "Required request body is missing";

	/**
	 * 微服务 运行时异常父类
	 * @param exception 客户端 异常
	 * @param request 请求
	 * @return 返回 验证结果
	 */
	@ResponseBody
	@ExceptionHandler(CloudRuntimeException.class)
	public Response<?> cloudRuntimeException(CloudRuntimeException exception, HttpServletRequest request) {

		String code = exception.getCode();
		String msg = exception.getMsg();

		log.error(String.format("%s：%s", code, msg), exception);

		return Response.error(code, msg);
	}

	/**
	 * 微服务 异常父类
	 * @param exception 客户端 异常
	 * @param request 请求
	 * @return 返回 验证结果
	 */
	@ResponseBody
	@ExceptionHandler(CloudException.class)
	public Response<?> clientException(CloudException exception, HttpServletRequest request) {

		String code = exception.getCode();
		String msg = exception.getMsg();

		log.error(String.format("%s：%s", code, msg), exception);

		return Response.error(code, msg);
	}

	// /**
	// * 客户端 异常父类
	// *
	// * @param exception 客户端 异常
	// * @param request 请求
	// * @return 返回 验证结果
	// */
	// @ResponseBody
	// @ExceptionHandler(ClientException.class)
	// public Response<?> clientException(ClientException exception, HttpServletRequest
	// request) {
	//
	// String code = exception.getCode();
	// String msg = exception.getMsg();
	//
	// log.error(String.format("%s：%s", code, msg), exception);
	//
	// return Response.error(code, msg);
	// }

	// /**
	// * Token 异常父类
	// *
	// * @param exception Token 异常父类
	// * @param request 请求
	// * @return 返回 验证结果
	// */
	// @ResponseBody
	// @ExceptionHandler(TokenException.class)
	// public Response<?> tokenException(TokenException exception, HttpServletRequest
	// request) {
	//
	// String code = exception.getCode();
	// String msg = exception.getMsg();
	//
	// log.error(String.format("%s：%s", code, msg), exception);
	//
	// // 清空 vuex
	// return ResponseMap.error(code, msg).put("clearVuex", true);
	// }

	/**
	 * sql 完整性约束违反异常
	 * @param exception sql 完整性约束违反异常
	 * @param request 请求
	 * @return 返回 验证结果
	 */
	@ResponseBody
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public Response<?> sqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException exception,
			HttpServletRequest request) {

		String sqlState = exception.getSQLState();
		int errorCode = exception.getErrorCode();
		String message = exception.getMessage();

		String code = CodeEnums.Q10001.code;
		String msg = CodeEnums.Q10001.msg;
		if (message != null) {
			if (message.matches(CodeEnums.Q10002_REGEX)) {
				code = CodeEnums.Q10002.code;
				msg = CodeEnums.Q10002.msg;
			}
			else if (message.matches(CodeEnums.Q10003_REGEX)) {
				code = CodeEnums.Q10003.code;
				msg = CodeEnums.Q10003.msg;
			}
		}

		log.error(msg, exception);

		return ResponseMap.error(code, msg).put("sqlState", sqlState).put("errorCode", errorCode);
	}

	/**
	 * 缺少 Servlet 请求参数异常
	 * @param exception 缺少 Servlet 请求参数异常
	 * @param request 请求
	 * @return 返回 验证结果
	 */
	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public Response<?> missingServletRequestParameterException(MissingServletRequestParameterException exception,
			HttpServletRequest request) {

		String message = exception.getMessage();
		String parameterName = exception.getParameterName();
		String parameterType = exception.getParameterType();

		log.error(String.format("%s：%s：%s：%s", CodeEnums.P00001.msg, message, parameterName, parameterType), exception);

		ResponseMap error = ResponseMap.error(CodeEnums.P00001.code, CodeEnums.P00001.msg);
		error.put(Constant.PARAMETER_NAME, parameterName);
		error.put(Constant.PARAMETER_TYPE, parameterType);

		return error;
	}

	// /**
	// * 当资源服务器处理请求时，表示令牌范围不足的异常
	// *
	// * @param exception 当资源服务器处理请求时，表示令牌范围不足的异常
	// * @param request 请求
	// * @return 返回 验证结果
	// */
	// @ResponseBody
	// @ExceptionHandler(InsufficientScopeException.class)
	// public Response<?> insufficientScopeException(InsufficientScopeException exception,
	// HttpServletRequest request) {
	//
	// log.error(CodeEnums.T00001.msg, exception);
	//
	// Map<String, String> additionalInformation = exception.getAdditionalInformation();
	//
	// ResponseMap error = ResponseMap.error(CodeEnums.T00001.code, CodeEnums.T00001.msg);
	//
	// if (additionalInformation != null) {
	// error.put(Constant.SCOPE, additionalInformation.get(Constant.SCOPE));
	// }
	//
	// return error;
	// }

	/**
	 * 不支持的请求类型
	 * @param exception 不支持的请求类型
	 * @param request 请求
	 * @return 返回 验证结果
	 */
	@ResponseBody
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public Response<?> httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception,
			HttpServletRequest request) {

		MediaType contentType = exception.getContentType();
		log.error(String.format("%s：%s", CodeEnums.B10001.msg, contentType), exception);

		Response<?> error = Response.error();
		error.setCode(CodeEnums.B10001.code);
		error.setMsg(String.format("%s：%s", CodeEnums.B10001.msg, contentType));
		return error;
	}

	/**
	 * 所需的请求正文无效、无效格式异常、无效属性
	 * @param exception 所需的请求正文无效、无效格式异常、无效属性
	 * @param request 请求
	 * @return 返回 验证结果
	 */
	@ResponseBody
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Response<?> httpMessageNotReadableException(HttpMessageNotReadableException exception,
			HttpServletRequest request) {

		Response<?> error = Response.error();

		Throwable cause = exception.getCause();
		if (cause instanceof InvalidFormatException invalidFormatException) {

			log.error(CodeEnums.B10004.msg, exception);

			error.setCode(CodeEnums.B10004.code);

			Object value = invalidFormatException.getValue();
			Class<?> targetType = invalidFormatException.getTargetType();
			List<JsonMappingException.Reference> pathList = invalidFormatException.getPath();
			String reference = FieldErrorUtils.reference(pathList);

			String name = targetType.getName();
			error.setMsg(String.format("%s：%s：转换为：%s 失败", CodeEnums.B10004.msg, value, name));
			error.setField(reference);

			log.error(String.valueOf(error));
		}
		else if (cause instanceof UnrecognizedPropertyException unrecognizedPropertyException) {

			String propertyName = unrecognizedPropertyException.getPropertyName();
			log.error(CodeEnums.B10005.msg, unrecognizedPropertyException);

			error.setCode(CodeEnums.B10005.code);
			error.setMsg(CodeEnums.B10005.msg);
			error.setField(propertyName);
		}
		else {
			String message = exception.getMessage();
			if (message != null && message.startsWith(REQUIRED_REQUEST_BODY_IS_MISSING)) {
				log.error(CodeEnums.B10006.msg, exception);

				error.setCode(CodeEnums.B10006.code);
				error.setMsg(CodeEnums.B10006.msg);
			}
			else {
				log.error(CodeEnums.B10002.msg, exception);

				error.setCode(CodeEnums.B10002.code);
				error.setMsg(CodeEnums.B10002.msg);
				error.setExplain(message);
			}
		}

		return error;
	}

	/**
	 * 方法参数无效异常
	 * @param exception 方法参数无效异常
	 * @param request 请求
	 * @return 返回 验证结果
	 */
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Response<?> methodArgumentNotValidException(MethodArgumentNotValidException exception,
			HttpServletRequest request) {

		ResponseMap error = ResponseMap.error();
		error.setCode(CodeEnums.B10003.code);
		error.setMsg(CodeEnums.B10003.msg);

		BindingResult bindingResult = exception.getBindingResult();
		List<FieldError> fieldErrorList = bindingResult.getFieldErrors();

		FieldErrorUtils.list(error, fieldErrorList);

		return error;
	}

}
