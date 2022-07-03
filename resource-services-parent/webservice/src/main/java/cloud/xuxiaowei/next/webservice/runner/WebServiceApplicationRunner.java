package cloud.xuxiaowei.next.webservice.runner;

import cloud.xuxiaowei.next.webservice.service.UserService;
import jakarta.xml.ws.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;

/**
 * WeService 配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Component
public class WebServiceApplicationRunner implements ApplicationRunner {

	private ServerProperties serverProperties;

	private UserService userService;

	@Autowired
	public void setServerProperties(ServerProperties serverProperties) {
		this.serverProperties = serverProperties;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		Integer port = serverProperties.getPort();

		Endpoint.publish(String.format("http://0.0.0.0:%s/userService", port == null ? 8081 : port + 1), userService);

	}

}
