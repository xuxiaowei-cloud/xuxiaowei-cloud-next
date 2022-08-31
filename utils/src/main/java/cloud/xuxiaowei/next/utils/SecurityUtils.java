package cloud.xuxiaowei.next.utils;

import cloud.xuxiaowei.next.utils.exception.CloudRuntimeException;
import cn.hutool.core.codec.Base64;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * 安全 工具类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
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
	 * sub - 主题声明标识作为 JWT 主题的主体
	 * @return 返回 客户ID
	 */
	public static String getSub() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof Jwt jwt) {
				Map<String, Object> claims = jwt.getClaims();
				Object sub = claims.get(JwtClaimNames.SUB);
				return sub == null ? null : sub.toString();
			}
		}
		return null;
	}

	/**
	 * sub - 主题声明标识作为 JWT 主题的主体
	 * @return 返回 客户ID
	 */
	public static String getClientId() {
		return getSub();
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
	 * 获取 用户名
	 * @param principal 主体
	 * @return 返回 用户名
	 */
	public static String getUserName(Principal principal) {
		if (principal == null) {
			return null;
		}
		return principal.getName();
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
				return getUserName(jwt);
			}
		}
		return null;
	}

	/**
	 * 根据 {@link Jwt} 获取 用户名
	 * @param jwt Jwt
	 * @return 返回 用户名
	 */
	public static String getUserName(Jwt jwt) {
		Map<String, Object> claims = jwt.getClaims();
		Object userName = claims.get("user_name");
		if (userName == null) {
			userName = claims.get("username");
			if (userName == null) {
				return null;
			}
		}
		return userName.toString();
	}

	/**
	 * 获取 用户ID
	 * @return 返回 用户ID
	 */
	public static Long getUsersId() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		return getUsersId(authentication);
	}

	/**
	 * 根据 验证 获取 用户ID
	 * @param authentication 验证
	 * @return 返回 用户ID
	 */
	public static Long getUsersId(Authentication authentication) {
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof Jwt jwt) {
				return getUsersId(jwt);
			}
		}
		return null;
	}

	/**
	 * 根据 Jwt 获取 用户ID
	 * @param jwt Jwt
	 * @return 返回 用户ID
	 */
	public static Long getUsersId(Jwt jwt) {
		Map<String, Object> claims = jwt.getClaims();
		Object usersId = claims.get(Constant.USERS_ID);
		if (usersId == null) {
			return null;
		}
		try {
			return Long.valueOf(usersId.toString());
		}
		catch (Exception e) {
			log.error("用户ID：" + usersId + " 处理成 Long 时异常", e);
			throw new CloudRuntimeException("用户ID：" + usersId + "不合法");
		}
	}

	/**
	 * 根据 authorization 获取 payload
	 * @param authorization 授权信息
	 * @return payload
	 */
	public static String getPayload(String authorization) {
		if (StringUtils.hasLength(authorization)) {
			String[] authorizationSplit = authorization.split("\\.");
			int length = authorizationSplit.length;
			// 下标为 1 的是 Payload
			int len = 2;
			if (length >= len) {
				String payloadEncodeStr = authorizationSplit[1];
				return Base64.decodeStr(payloadEncodeStr);
			}
		}
		return null;
	}

	/**
	 * 根据 authorization 获取 payload
	 * @param authorization 授权信息
	 * @return payload
	 */
	public static Map<String, ?> getPayloadMap(String authorization) {
		String payload = getPayload(authorization);
		if (StringUtils.hasLength(payload)) {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			try {
				return objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {
				});
			}
			catch (JsonProcessingException e) {
				log.error("{}：转换为JSON异常", payload, e);
			}
		}
		return new HashMap<>(2);
	}

	/**
	 * 根据 authorization 获取 payload
	 * @param authorization 授权信息
	 * @return payload
	 */
	public static Map<String, String> getPayloadStringMap(String authorization) {
		Map<String, ?> payloadMap = getPayloadMap(authorization);
		Map<String, String> map = new HashMap<>(8);
		for (Map.Entry<String, ?> entry : payloadMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value == null) {
				map.put(key, null);
			}
			else {
				// 此处待优化
				map.put(key, value.toString());
			}
		}
		return map;
	}

	/**
	 * 检查 payload 是否为 JSON
	 * @param payload 授权信息
	 * @return payload
	 */
	public static String inspectPayload(String payload) {
		if (StringUtils.hasLength(payload)) {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			try {
				objectMapper.readValue(payload, Map.class);
				return payload;
			}
			catch (JsonProcessingException e) {
				log.error("{}：转换为JSON异常", payload, e);
			}
		}
		return null;
	}

}
