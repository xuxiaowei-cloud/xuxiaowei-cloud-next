package cloud.xuxiaowei.next.passport.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信小程序属性配置类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@Component
@ConfigurationProperties("wx.miniapp")
public class WxMaProperties {

    /**
     * 设置微信小程序的appid.
     */
    private String appid;

    /**
     * 设置微信小程序的Secret.
     */
    private String secret;

}
