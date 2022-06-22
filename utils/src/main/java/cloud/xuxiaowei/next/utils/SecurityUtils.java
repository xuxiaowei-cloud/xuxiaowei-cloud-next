package cloud.xuxiaowei.next.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;

/**
 * 安全 工具类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class SecurityUtils {

	/**
	 * 获取 Token
	 * @return 返回 Token
	 */
	public static String getTokenValue() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof Jwt jwt) {
				return jwt.getTokenValue();
			}
		}
		return null;
	}

	/**
	 * 获取 Token
	 * @return 返回 Token
	 */
	public static String getTokenValue(Authentication authentication) {
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof Jwt jwt) {
				return jwt.getTokenValue();
			}
		}
		return null;
	}

	/**
	 * 获取 用户名
	 * @return 返回 用户名
	 */
	public static String getUserName() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		return getUserName(authentication);
	}

	/**
	 * 根据 验证 获取 用户名
	 * @param authentication 验证
	 * @return 返回 用户名
	 */
	public static String getUserName(Authentication authentication) {
		if (authentication != null) {
			String name = authentication.getName();
			if (name != null) {
				return name;
			}

			Object principal = authentication.getPrincipal();
			if (principal instanceof Jwt jwt) {
				Map<String, Object> claims = jwt.getClaims();
				Object userName = claims.get("user_name");
				if (userName == null) {
					return null;
				}
				return userName.toString();
			}
		}
		return null;
	}

}
