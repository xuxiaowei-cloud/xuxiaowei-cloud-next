package cloud.xuxiaowei.next.webservice.configuration;

import cloud.xuxiaowei.next.webservice.service.UserService;
import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WebService 配置
 *
 * @author 徐晓伟
 * @since 0.0.1
 */
@Configuration
public class WebServiceConfiguration {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * CXF
	 * @return 返回 CXF {@link Bean}
	 */
	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
		return new SpringBus();
	}

	/**
	 * 注册 CXF 前缀 Servlet
	 * @return 返回 CXF {@link ServletRegistrationBean}
	 */
	@Bean
	public ServletRegistrationBean<CXFServlet> cxfServlet() {
		return new ServletRegistrationBean<>(new CXFServlet(), "/cxf/*");
	}

	/**
	 * 用户 WebService 接口
	 * @return 返回 公共 WebService 桶 {@link Endpoint}
	 */
	@Bean
	public Endpoint userServiceEndpoint() {
		EndpointImpl userServiceEndpointImpl = new EndpointImpl(userService);
		userServiceEndpointImpl.publish("/userService");

		userServiceEndpointImpl.getInInterceptors().add(new LoggingInInterceptor());
		userServiceEndpointImpl.getOutInterceptors().add(new LoggingOutInterceptor());

		return userServiceEndpointImpl;
	}

}
