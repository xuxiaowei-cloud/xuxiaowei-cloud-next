package cloud.xuxiaowei.next.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 微服务 Session 配置
 * <p>
 * 运行时刷新
 *
 * @author xuxiaowei
 * @see DefaultCookieSerializer
 * @since 0.0.1
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties("cloud.session")
public class CloudSessionProperties {

    /**
     * 过期时间
     */
    private long timeout = 12;

    /**
     * 过期时间单位
     */
    private TimeUnit unit = TimeUnit.HOURS;

}
