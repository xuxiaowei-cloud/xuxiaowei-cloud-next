package cloud.xuxiaowei.next.passport.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * OAuth 2
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@RestController
@RequestMapping("/oauth2")
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class OAuth2Controller {

    /**
     * 检查 Token
     *
     * @param request        请求
     * @param response       响应
     * @param authentication 授权信息
     * @return 返回 检查 Token 结果
     */
    @RequestMapping("/check_token")
    public Map<String, Object> checkToken(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Map<String, Object> map = new HashMap<>(4);
        if (authentication != null) {
            Set<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
            map.put("authorities", authorities);
            map.put("active", true);
        }
        return map;
    }

}
