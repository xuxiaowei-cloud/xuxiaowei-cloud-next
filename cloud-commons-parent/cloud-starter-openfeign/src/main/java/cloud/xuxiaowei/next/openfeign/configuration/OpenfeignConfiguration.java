package cloud.xuxiaowei.next.openfeign.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * openfeign 配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
@EnableFeignClients("cloud.xuxiaowei.next")
public class OpenfeignConfiguration {

}
