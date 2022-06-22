package cloud.xuxiaowei.next.core.properties;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.stereotype.Component;

/**
 * 微服务 Cookie 配置
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
@ConfigurationProperties("cloud.cookie")
public class CloudCookieProperties {

	/**
	 * 设置 Cookie 的路径。
	 * <p>
	 * 默认是使用来自 {@link HttpServletRequest} 的上下文路径。
	 * <p>
	 * 如果为 null，则将使用默认的上下文路径。
	 *
	 * @see DefaultCookieSerializer#setCookiePath(String)
	 */
	private String cookiePath;

	/**
	 * Cookie Name
	 * <p>
	 * 不能为空
	 *
	 * @see DefaultCookieSerializer#setCookieName(String)
	 */
	private String cookieName = "SESSION";

	/**
	 * 设置 Cookie 的 maxAge 属性。
	 * <p>
	 * 默认是在浏览器关闭时删除 cookie。
	 * <p>
	 * {@link Cookie} 的 {@link Cookie#getMaxAge} 属性（以秒为单位）
	 * <p>
	 * 1209600 = 60 * 60 * 24 * 14
	 *
	 * @see DefaultCookieSerializer#setCookieMaxAge(int)
	 */
	private Integer cookieMaxAge = 60 * 60 * 24 * 14;

	/**
	 * 设置显式域名。
	 * <p>
	 * 这允许当请求来自 www.example.com 时使用“example.com”域。
	 * <p>
	 * 这允许跨子域共享 cookie。
	 * <p>
	 * 默认是使用当前域。
	 *
	 * @see DefaultCookieSerializer#setDomainName(String)
	 */
	private String cookieDomain = "example.next.xuxiaowei.cloud";

	/**
	 * 设置是否应使用 cookie 值的 Base64 编码。
	 * <p>
	 * 这对于支持 <a href="https://tools.ietf.org/html/rfc6265">RFC 6265</a> 很有价值，RFC 6265 建议对
	 * cookie 值使用 Base 64 编码。
	 *
	 * @see DefaultCookieSerializer#setUseBase64Encoding(boolean)
	 */
	private boolean useBase64Encoding = true;

}
