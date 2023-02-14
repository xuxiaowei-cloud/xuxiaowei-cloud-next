package cloud.xuxiaowei.next.gateway.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * SpringDoc 配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
public class SpringdocConfiguration {

	@Bean
	@Lazy(false)
	public List<GroupedOpenApi> apis(SwaggerUiConfigParameters swaggerUiConfigParameters,
			RouteDefinitionLocator locator) {
		List<GroupedOpenApi> groups = new ArrayList<>();
		List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
		assert definitions != null;
		definitions.forEach(routeDefinition -> {
			String id = routeDefinition.getId();
			if (!"openapi".equals(id)) {
				URI uri = routeDefinition.getUri();
				String host = uri.getHost();
				swaggerUiConfigParameters.addGroup(host);
				GroupedOpenApi build = GroupedOpenApi.builder().pathsToMatch("/" + host + "/**").group(host).build();
				groups.add(build);
			}
		});
		return groups;
	}

}
