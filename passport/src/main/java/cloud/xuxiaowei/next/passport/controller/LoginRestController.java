package cloud.xuxiaowei.next.passport.controller;

import cloud.xuxiaowei.next.core.properties.CloudClientProperties;
import cloud.xuxiaowei.next.utils.CodeEnums;
import cloud.xuxiaowei.next.utils.Response;
import cloud.xuxiaowei.next.utils.map.ResponseMap;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.UUID;

import static cloud.xuxiaowei.next.utils.Constant.UNDEFINED;

/**
 * 登录
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginRestController {

	private CloudClientProperties cloudClientProperties;

	@Autowired
	public void setCloudClientProperties(CloudClientProperties cloudClientProperties) {
		this.cloudClientProperties = cloudClientProperties;
	}

	/**
	 * 登录成功
	 * @param request 请求
	 * @param response 响应
	 * @param session Session，不存在时自动创建
	 * @param redirectUri 授权重定向地址
	 * @param homePage 主页
	 * @return 返回 登录成功提示语
	 */
	@RequestMapping("/success")
	public Response<?> success(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			String redirectUri, String homePage) {
		String state = UUID.randomUUID().toString();
		session.setAttribute(cloudClientProperties.getStateName(), state);

		ResponseMap ok = ResponseMap.ok("登录成功");

		if (UrlUtils.isAbsoluteUrl(redirectUri)) {
			log.info("使用登录参数中的授权重定向地址：{}", redirectUri);
		}
		else {
			redirectUri = cloudClientProperties.getRedirectUri();
			log.info("使用默认授权重定向地址：{}", redirectUri);
		}

		if (StringUtils.hasText(homePage) && !UNDEFINED.equals(homePage)) {
			try {
				new URL(homePage);
				log.info("使用登录参数中的登录成功主页地址：{}", homePage);
			}
			catch (Exception e) {
				log.error("非法登录成功主页地址：", e);
				homePage = cloudClientProperties.getHomePage();
				log.warn("使用默认登录成功主页地址：{}", homePage);
			}
		}
		else {
			homePage = cloudClientProperties.getHomePage();
			log.info("使用默认登录成功主页地址：{}", homePage);
		}
		// 将登录成功主页放入Session中，用于授权成功后页面跳转
		session.setAttribute(state, homePage);

		String authorizeUri = cloudClientProperties.authorizeUri(state, redirectUri);
		String checkTokenUri = cloudClientProperties.getCheckTokenUri();

		return ok.put("authorizeUri", authorizeUri).put("checkTokenUri", checkTokenUri);
	}

	/**
	 * 登录成功主页
	 * @param request 请求
	 * @param response 响应
	 * @param session Session，不存在时自动创建
	 * @return 返回 登录成功主页
	 */
	@RequestMapping("/home-page")
	public Response<?> homePage(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		// 返回的主页可根据权限，或者用户设置的默认系统等信息返回不同的主页
		String homePage = cloudClientProperties.getHomePage();

		return Response.ok(homePage, CodeEnums.OK.msg);
	}

}
