package cloud.xuxiaowei.next.loadbalancer.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * {@link LoadBalanced} 配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
public class LoadBalancedConfiguration {

	private ReactorLoadBalancerExchangeFilterFunction reactorLoadBalancerExchangeFilterFunction;

	@Autowired
	public void setReactorLoadBalancerExchangeFilterFunction(
			ReactorLoadBalancerExchangeFilterFunction reactorLoadBalancerExchangeFilterFunction) {
		this.reactorLoadBalancerExchangeFilterFunction = reactorLoadBalancerExchangeFilterFunction;
	}

	/**
	 * 支持负载均衡的 {@link RestTemplate}
	 * @return 返回 支持负载均衡的 {@link RestTemplate}
	 */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * 支持负载均衡的 {@link WebClient}
	 * @return 返回 支持负载均衡的 {@link WebClient}
	 */
	@Bean
	@LoadBalanced
	public WebClient webClient() {
		return WebClient.builder().filter(reactorLoadBalancerExchangeFilterFunction).build();
	}

}
