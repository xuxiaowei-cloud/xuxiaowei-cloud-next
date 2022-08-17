package cloud.xuxiaowei.next.system.configuration;

import cloud.xuxiaowei.next.system.interceptor.EncryptHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC 配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
public class WebMvcConfigurerConfiguration implements WebMvcConfigurer {

	private EncryptHandlerInterceptor encryptHandlerInterceptor;

	@Autowired
	public void setEncryptHandlerInterceptor(EncryptHandlerInterceptor encryptHandlerInterceptor) {
		this.encryptHandlerInterceptor = encryptHandlerInterceptor;
	}

	@Override
	public void addInterceptors(@NonNull InterceptorRegistry registry) {

		// AES 加密拦截器
		registry.addInterceptor(encryptHandlerInterceptor).addPathPatterns("/**");

	}

}