package cloud.xuxiaowei.next.authorizationserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

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
            UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());

            List<String> authorities = new ArrayList<>();
            for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
                String authority = grantedAuthority.getAuthority();
                authorities.add(authority);
            }

            map.put("authorities", authorities);
            map.put("active", true);
        }
        return map;
    }

}
