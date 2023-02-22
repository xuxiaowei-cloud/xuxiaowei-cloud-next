package cloud.xuxiaowei.next.passport.controller;

import cloud.xuxiaowei.next.core.properties.CloudJwkKeyProperties;
import cloud.xuxiaowei.next.passport.service.IOauth2AuthorizationService;
import cloud.xuxiaowei.next.system.annotation.ControllerAnnotation;
import cloud.xuxiaowei.next.utils.Response;
import cn.hutool.crypto.asymmetric.RSA;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.interfaces.RSAPublicKey;

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

	private CloudJwkKeyProperties cloudJwkKeyProperties;

	private IOauth2AuthorizationService oauth2AuthorizationService;

	@Autowired
	public void setCloudJwkKeyProperties(CloudJwkKeyProperties cloudJwkKeyProperties) {
		this.cloudJwkKeyProperties = cloudJwkKeyProperties;
	}

	@Autowired
	public void setOauth2AuthorizationService(IOauth2AuthorizationService oauth2AuthorizationService) {
		this.oauth2AuthorizationService = oauth2AuthorizationService;
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
			String accessToken) {

		// 此处可记录用户退出登录的时间及IP等信息

		session.invalidate();

		RSA rsa = new RSA(null, cloudJwkKeyProperties.rsaPublicKey());

		NimbusJwtDecoder.PublicKeyJwtDecoderBuilder publicKeyJwtDecoderBuilder = NimbusJwtDecoder
			.withPublicKey((RSAPublicKey) rsa.getPublicKey());
		NimbusJwtDecoder nimbusJwtDecoder = publicKeyJwtDecoderBuilder.build();

		try {
			Jwt jwt = nimbusJwtDecoder.decode(accessToken);
			String subject = jwt.getSubject();
			log.info("用户：{} 退出登录", subject);
			boolean remove = oauth2AuthorizationService.removeByAccessTokenValue(accessToken);
			log.info("删除用户：{} 授权 Token：{} 结果：{}", subject, accessToken, remove);
		}
		catch (Exception e) {
			log.error("退出时，解密：{} 异常：", accessToken, e);
		}

		return Response.ok();
	}

}
