package cloud.xuxiaowei.next.passport.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OAuth 2
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/oauth2")
public class Oauth2RestController {

    /**
     * 获取 Token
     *
     * @param request  请求
     * @param response 响应
     * @return 返回 Token
     */
    @RequestMapping("/authorization-server-oidc")
    public OAuth2AuthorizedClient authorizationServerOidc(HttpServletRequest request, HttpServletResponse response, @RegisteredOAuth2AuthorizedClient("authorization-server-oidc") OAuth2AuthorizedClient authorizedClient) {

        return authorizedClient;
    }

}
