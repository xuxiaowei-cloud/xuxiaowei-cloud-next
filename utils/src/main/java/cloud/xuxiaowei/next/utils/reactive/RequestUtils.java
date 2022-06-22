package cloud.xuxiaowei.next.utils.reactive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public static Map<String, List<String>> getHeadersMap(ServerHttpRequest request) {
		Map<String, List<String>> map = new HashMap<>(8);
		HttpHeaders httpHeaders = request.getHeaders();
		for (String headerName : httpHeaders.keySet()) {
			List<String> headerValues = httpHeaders.get(headerName);
			map.put(headerName, headerValues);
		}
		return map;
	}

	/**
	 * 获取 Headers JSON
	 * @param request 请求
	 * @return 返回 Headers JSON
	 */
	public static String getHeadersJson(ServerHttpRequest request) {
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
	public static String getUserAgent(ServerHttpRequest request) {
		HttpHeaders httpHeaders = request.getHeaders();
		return httpHeaders.getFirst(HttpHeaders.USER_AGENT);
	}

	/**
	 * 获取来源
	 * @param request 请求
	 * @return 返回 来源
	 */
	public static String getOrigin(@NonNull ServerHttpRequest request) {
		HttpHeaders headers = request.getHeaders();
		return headers.getOrigin();
	}

	/**
	 * 获取来源
	 * @param exchange 服务器网络交换
	 * @return 返回 来源
	 */
	public static String getOrigin(@NonNull ServerWebExchange exchange) {
		ServerHttpRequest request = exchange.getRequest();
		return getOrigin(request);
	}

	/**
	 * 获取域名
	 * @param request 请求
	 * @return 返回 域名
	 */
	public static String getSchemeHost(@NonNull ServerHttpRequest request) {
		URI uri = request.getURI();
		String scheme = uri.getScheme();
		String host = uri.getHost();
		return scheme + "://" + host;
	}

	/**
	 * 获取域名
	 * @param exchange 服务器网络交换
	 * @return 返回 域名
	 */
	public static String getSchemeHost(@NonNull ServerWebExchange exchange) {
		ServerHttpRequest request = exchange.getRequest();
		return getSchemeHost(request);
	}

}
