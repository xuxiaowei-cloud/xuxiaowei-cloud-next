package cloud.xuxiaowei.next.loadbalancer.configuration;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Set;

/**
 * {@link HttpExchange} 服务配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class HttpExchangeConfiguration {

	private ApplicationContext applicationContext;

	@Autowired
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Autowired
	public void setWebClient(WebClient webClient) {

		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
		ConfigurableListableBeanFactory beanFactory = configurableApplicationContext.getBeanFactory();

		HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
			.builder(WebClientAdapter.forClient(webClient))
			.build();

		// 此处应该配置文件
		Reflections reflections = reflections("cloud.xuxiaowei.next.passport.web.service");
		// 获取使用指定注解的类
		Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(HttpExchange.class);
		for (Class<?> classes : typesAnnotatedWith) {
			String simpleName = classes.getSimpleName();

			Object client = httpServiceProxyFactory.createClient(classes);

			// 方式一：使用 hutool
			// SpringUtil.registerBean(simpleName, client);

			// 方式二：直接使用 ApplicationContext 操作
			beanFactory.autowireBean(client);
			beanFactory.registerSingleton(simpleName, client);
		}
	}

	public Reflections reflections(String... packages) {
		return new Reflections(new ConfigurationBuilder().forPackages(packages).addScanners(Scanners.TypesAnnotated));
	}

}
