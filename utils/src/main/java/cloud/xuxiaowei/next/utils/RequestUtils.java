package cloud.xuxiaowei.next.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * 请求 工具类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
public class RequestUtils {

	/**
	 * 获取 Headers Map
	 * @param request 请求
	 * @return 返回 Headers Map
	 */
	public static Map<String, List<String>> getHeadersMap(@NonNull HttpServletRequest request) {
		Map<String, List<String>> map = new HashMap<>(8);
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			Enumeration<String> headerValues = request.getHeaders(headerName);
			List<String> headerValuesList = Collections.list(headerValues);
			map.put(headerName, headerValuesList);
		}
		return map;
	}

	/**
	 * 获取 Headers JSON
	 * @param request 请求
	 * @return 返回 Headers JSON
	 */
	public static String getHeadersJson(@NonNull HttpServletRequest request) {
		Map<String, List<String>> headersMap = getHeadersMap(request);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(headersMap);
		}
		catch (JsonProcessingException e) {
			log.error("使用jackson将请求头处理为JSON异常：", e);
		}
		return null;
	}

	/**
	 * 获取标识
	 * @param request 请求
	 * @return 返回 标识
	 */
	public static String getUserAgent(@NonNull HttpServletRequest request) {
		return request.getHeader(HttpHeaders.USER_AGENT);
	}

	/**
	 * 请求参数流
	 * @param request 请求
	 * @return 返回 请求参数流
	 * @throws IOException 读取流异常
	 * @see HttpServletRequest#getContentLength() 请关注是否为负数
	 */
	public static String getInputStream(HttpServletRequest request) throws IOException {
		ServletInputStream inputStream = request.getInputStream();
		int contentLength = request.getContentLength();
		if (contentLength == -1) {
			// GET 请求时
			// DELETE、HEAD、OPTIONS 请求未设置流时
			return null;
		}
		else {
			byte[] bytes = new byte[contentLength];
			String characterEncoding = request.getCharacterEncoding();
			inputStream.read(bytes);
			return new String(bytes, characterEncoding);
		}
	}

	/**
	 * 请求参数流
	 * @param request 请求
	 * @return 返回 请求参数流
	 * @throws IOException 读取流异常
	 * @see HttpServletRequest#getContentLength() 无需关注是否为负数
	 */
	public static String copyInputStream(HttpServletRequest request) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		IOUtils.copy(request.getInputStream(), byteArrayOutputStream);
		return byteArrayOutputStream.toString();
	}

}
