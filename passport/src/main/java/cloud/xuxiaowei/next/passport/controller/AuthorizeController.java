package cloud.xuxiaowei.next.passport.controller;

import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 授权同意页面
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Controller
@RequestMapping("/oauth2.1")
public class AuthorizeController {

	/**
	 * 授权同意页面
	 * @param principal 用户信息
	 * @param model 页面中的值
	 * @param scope 范围
	 * @param clientId 客户ID
	 * @param state 状态码
	 * @return 返回 授权同意页面
	 */
	@RequestMapping("/authorize")
	public String authorize(Principal principal, Model model, @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
			@RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
			@RequestParam(OAuth2ParameterNames.STATE) String state) {

		List<String> convertScope;
		if (scope == null) {
			convertScope = Collections.emptyList();
		}
		else {
			convertScope = Arrays.asList(StringUtils.delimitedListToStringArray(scope, " "));
		}

		model.addAttribute("requestUri", "/passport/oauth2/authorize");
		model.addAttribute("clientId", clientId);
		model.addAttribute("convertScope", convertScope);
		model.addAttribute("principal", principal);
		model.addAttribute("state", state);

		return "oauth2.1/authorize";
	}

}
