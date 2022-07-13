package cloud.xuxiaowei.next.openfeign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

/**
 * header 传递授权信息
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
public class AuthorizationRequestInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate requestTemplate) {

		// RequestContextHolder.getRequestAttributes() 不为空
		// hystrix.command.default.execution.isolation.strategy = SEMAPHORE
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes == null) {
			log.warn("RequestContextHolder.getRequestAttributes() 为空，无法在 header 中传递授权信息");
			return;
		}

		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
		HttpServletRequest request = servletRequestAttributes.getRequest();
		Enumeration<String> headerNames = request.getHeaderNames();

		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();

				// 注意：不要传递 Content-Length，防止接收不到响应

				if (HttpHeaders.AUTHORIZATION.equalsIgnoreCase(headerName)) {
					String headerValue = request.getHeader(headerName);
					requestTemplate.header(headerName, headerValue);
				}
			}
		}

	}

}
