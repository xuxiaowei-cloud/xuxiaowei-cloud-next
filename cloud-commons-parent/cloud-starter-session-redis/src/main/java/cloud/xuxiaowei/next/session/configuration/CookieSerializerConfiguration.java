package cloud.xuxiaowei.next.session.configuration;

import cloud.xuxiaowei.next.core.properties.CloudCookieProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * Cookie序列化 与 Session共享 配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
public class CookieSerializerConfiguration {

    private CloudCookieProperties cloudCookieProperties;

    @Autowired
    public void setCloudCookieProperties(CloudCookieProperties cloudCookieProperties) {
        this.cloudCookieProperties = cloudCookieProperties;
    }

    /**
     * 在主域中储存Cookie，子域中共享Cookie
     */
    @Bean
    public CookieSerializer cookieSerializer() {

        // 默认 Cookie 序列化
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();

        // Cookie名字，默认为 SESSION
        defaultCookieSerializer.setCookieName(cloudCookieProperties.getCookieName());

        // 域，这允许跨子域共享cookie，默认设置是使用当前域。
        defaultCookieSerializer.setDomainName(cloudCookieProperties.getCookieDomain());

        // Session Cookie 是否使用 Base64
        defaultCookieSerializer.setUseBase64Encoding(cloudCookieProperties.isUseBase64Encoding());

        // Cookie 过期时间
        defaultCookieSerializer.setCookieMaxAge(cloudCookieProperties.getCookieMaxAge());

        // Cookie的路径。
        defaultCookieSerializer.setCookiePath(cloudCookieProperties.getCookiePath());

        return defaultCookieSerializer;
    }

}
