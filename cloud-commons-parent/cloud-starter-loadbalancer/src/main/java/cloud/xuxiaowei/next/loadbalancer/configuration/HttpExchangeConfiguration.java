package cloud.xuxiaowei.next.loadbalancer.configuration;

import cloud.xuxiaowei.next.core.properties.CloudHttpExchangeProperties;
import cloud.xuxiaowei.next.utils.ReflectionsUtils;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
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

	@Autowired
	public void setHttpExchange(ApplicationContext applicationContext, WebClient webClient,
			CloudHttpExchangeProperties cloudHttpExchangeProperties) {

		String[] packages = cloudHttpExchangeProperties.getPackages();

		if (packages != null && packages.length > 0) {

			ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
			ConfigurableListableBeanFactory beanFactory = configurableApplicationContext.getBeanFactory();

			HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
				.builder(WebClientAdapter.forClient(webClient))
				.build();

			Reflections reflections = ReflectionsUtils.typesAnnotated(packages);
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
	}

}
