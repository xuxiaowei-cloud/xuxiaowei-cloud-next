package cloud.xuxiaowei.next.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 微服务 白名单 配置
 * <p>
 * 不进行权限验证
 * <p>
 * 运行时刷新
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties("cloud.white-list")
public class CloudWhiteListProperties {

    /**
     * 允许端点的IP
     */
    private List<String> actuatorIpList = Collections.emptyList();

    /**
     * 忽略
     */
    private List<String> ignores = Collections.emptyList();

    /**
     * 服务列表
     */
    private List<Service> services = Collections.emptyList();

    /**
     * 服务
     *
     * @author xuxiaowei
     * @since 0.0.1
     */
    @Data
    public static class Service {

        /**
         * 服务名
         */
        private String name;

        /**
         * 路径
         * <p>
         * 放行所有：/**
         */
        private List<String> pathList = Collections.emptyList();

    }

}
