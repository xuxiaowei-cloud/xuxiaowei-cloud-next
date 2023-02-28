package cloud.xuxiaowei.next.passport.controller;

import cloud.xuxiaowei.next.core.properties.CloudRememberMeProperties;
import cn.hutool.crypto.asymmetric.RSA;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 主页
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Controller
public class IndexController {

	public static final String RSA_PRIVATE_KEY_BASE64 = "RSA_PRIVATE_KEY_BASE64";

	public static final String RSA_PUBLIC_KEY_BASE64 = "RSA_PUBLIC_KEY_BASE64";

	private CloudRememberMeProperties cloudRememberMeProperties;

	@Autowired
	public void setCloudRememberMeProperties(CloudRememberMeProperties cloudRememberMeProperties) {
		this.cloudRememberMeProperties = cloudRememberMeProperties;
	}

	/**
	 * 主页
	 * @param request 请求
	 * @param response 响应
	 * @param session Session，不存在时自动创建
	 * @param model 页面中的值
	 * @return 返回 主页
	 */
	@RequestMapping(value = { "/", "/index" })
	public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {

		String rememberMeParameter = cloudRememberMeProperties.getRememberMeParameter();
		model.addAttribute("rememberMeParameter", rememberMeParameter);

		Object privateKeyBase64 = session.getAttribute(RSA_PRIVATE_KEY_BASE64);
		Object publicKeyBase64 = session.getAttribute(RSA_PUBLIC_KEY_BASE64);
		if (privateKeyBase64 == null || publicKeyBase64 == null) {
			// 创建秘钥对
			RSA generate = new RSA();

			// 获取私钥
			privateKeyBase64 = generate.getPrivateKeyBase64();
			// 获取公钥
			publicKeyBase64 = generate.getPublicKeyBase64();

			session.setAttribute(RSA_PRIVATE_KEY_BASE64, privateKeyBase64);
			session.setAttribute(RSA_PUBLIC_KEY_BASE64, publicKeyBase64);
		}

		model.addAttribute(RSA_PUBLIC_KEY_BASE64, publicKeyBase64);

		return "index";
	}

}
