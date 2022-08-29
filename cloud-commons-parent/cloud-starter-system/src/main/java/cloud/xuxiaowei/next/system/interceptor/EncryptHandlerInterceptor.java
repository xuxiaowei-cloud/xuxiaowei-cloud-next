package cloud.xuxiaowei.next.system.interceptor;

import cloud.xuxiaowei.next.system.annotation.EncryptAnnotation;
import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.Encrypt;
import cloud.xuxiaowei.next.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;

/**
 * AES 加密拦截器
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Component
public class EncryptHandlerInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull Object handler) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();

		// 判断是否存在加密注解
		boolean annotationPresent = method.isAnnotationPresent(EncryptAnnotation.class);

		EncryptAnnotation encryptAnnotation = method.getAnnotation(EncryptAnnotation.class);
		if (annotationPresent) {

			// 接口的客户配置
			EncryptAnnotation.ClientId[] clients = encryptAnnotation.client();

			// 当前调用的客户ID
			String clientId = SecurityUtils.getClientId();

			// 默认配置
			Encrypt.AesVersion aesVersion = encryptAnnotation.value();

			if (StringUtils.hasText(clientId)) {
				// 存在客户ID，使用接口中指定客户的配置

				for (EncryptAnnotation.ClientId clientIdEncryptAnnotation : clients) {
					// 遍历接口的客户配置

					if (clientId.equals(clientIdEncryptAnnotation.cloudId())) {
						// 匹配接口的客户配置是否与当前用户相同
						// 如果相同，将匹配的客户配置返回
						aesVersion = clientIdEncryptAnnotation.value();

						// 如果该接口匹配到了当前客户对应的客户配置，将当前客户ID放入响应头中
						response.setHeader(OAuth2TokenIntrospectionClaimNames.CLIENT_ID, clientId);
					}
				}

			}

			// 将加密注解放入响应头中
			response.setHeader(Constant.ENCRYPT, aesVersion.version);
		}

		return true;
	}

	@Override
	public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull Object handler, Exception ex) throws Exception {

	}

}
