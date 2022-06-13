package cloud.xuxiaowei.next.loadbalancer.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * {@link LoadBalanced} 配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
public class LoadBalancedConfiguration {

    /**
     * 支持负载均衡的 {@link RestTemplate}
     *
     * @return 返回 支持负载均衡的 {@link RestTemplate}
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
