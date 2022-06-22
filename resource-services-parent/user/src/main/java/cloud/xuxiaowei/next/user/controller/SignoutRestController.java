package cloud.xuxiaowei.next.user.controller;

import cloud.xuxiaowei.next.core.properties.CloudCookieProperties;
import cloud.xuxiaowei.next.system.annotation.ControllerAnnotation;
import cloud.xuxiaowei.next.utils.Response;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 退出
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/signout")
public class SignoutRestController {

	private CloudCookieProperties cloudCookieProperties;

	@Autowired
	public void setCloudCookieProperties(CloudCookieProperties cloudCookieProperties) {
		this.cloudCookieProperties = cloudCookieProperties;
	}

	/**
	 * 退出
	 * @param request 请求
	 * @param response 响应
	 * @param session Session，不存在时自动创建
	 * @return 返回 退出提示语
	 */
	@ControllerAnnotation(description = "退出")
	@RequestMapping
	public Response<?> index(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Authentication authentication) {

		// 此处可记录用户退出登录的时间及IP等信息

		String cookieDomain = cloudCookieProperties.getCookieDomain();
		String cookieName = cloudCookieProperties.getCookieName();
		String cookiePath = cloudCookieProperties.getCookiePath();

		Cookie cookie = new Cookie(cookieName, null);
		cookie.setPath(cookiePath == null ? "/" : cookieDomain);
		cookie.setDomain(cookieDomain);
		cookie.setMaxAge(0);
		response.addCookie(cookie);

		return Response.ok();
	}

}
