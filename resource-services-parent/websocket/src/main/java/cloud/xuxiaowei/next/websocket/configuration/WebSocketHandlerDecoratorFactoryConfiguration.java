package cloud.xuxiaowei.next.websocket.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

/**
 * {@link WebSocketHandler} 工厂
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
public class WebSocketHandlerDecoratorFactoryConfiguration implements WebSocketHandlerDecoratorFactory {

	private ApplicationContext applicationContext;

	@Autowired
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	@NonNull
	public WebSocketHandler decorate(@NonNull WebSocketHandler handler) {
		WebSocketHandlerDecoratorConfiguration decoratorConfiguration = new WebSocketHandlerDecoratorConfiguration(
				handler);
		decoratorConfiguration.setApplicationContext(applicationContext);
		return decoratorConfiguration;
	}

}
