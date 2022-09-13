package cloud.xuxiaowei.next.system.service.impl;

import cloud.xuxiaowei.next.core.properties.CloudSessionProperties;
import cloud.xuxiaowei.next.system.service.SessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * {@link HttpSession} 服务接口 实现类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Service
public class SessionServiceImpl implements SessionService {

	private HttpSession session;

	private RedisTemplate<String, Object> redisTemplate;

	private StringRedisTemplate stringRedisTemplate;

	private CloudSessionProperties cloudSessionProperties;

	@Autowired
	public void setSession(HttpSession session) {
		this.session = session;
	}

	@Autowired
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Autowired
	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	@Autowired
	public void setCloudSessionProperties(CloudSessionProperties cloudSessionProperties) {
		this.cloudSessionProperties = cloudSessionProperties;
	}

	/**
	 * 获取 Token
	 * @return 返回 Token
	 */
	@Override
	public String getTokenValue() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof Jwt jwt) {
			return jwt.getTokenValue();
		}
		return null;
	}

	/**
	 * 计算令牌的MD5值
	 * @param value 令牌
	 * @return 返回 令牌的MD5值
	 */
	@Override
	public String extractTokenKey(String value) {
		if (value == null) {
			return null;
		}
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5 算法不可用。致命的（应该在 JDK 中）。");
		}

		byte[] bytes = digest.digest(value.getBytes(StandardCharsets.UTF_8));
		return String.format("%032x", new BigInteger(1, bytes));
	}

	/**
	 * 获取 授权Token ID
	 * <p>
	 * 身份验证未成功时（或：未进行身份验证），将返回 {@link HttpSession#getId()}
	 * @return 返回 授权Token ID，身份验证未成功时（或：未进行身份验证），将返回 {@link HttpSession#getId()}
	 */
	@Override
	public String tokenId() {
		String tokenValue = getTokenValue();
		if (tokenValue == null) {
			return session.getId();
		}
		return extractTokenKey(tokenValue);
	}

	/**
	 * 获取 Session（Redis） ID
	 * @return 返回 Session（Redis） ID
	 */
	private String sessionId() {
		return "session:" + tokenId();
	}

	/**
	 * 设置 Session（Redis） 中的值
	 * @param key 键
	 * @param value 值
	 */
	@Override
	public void setAttribute(@NonNull String key, Object value) {
		String sessionId = sessionId();
		redisTemplate.opsForHash().put(sessionId, key, value);

		// 过期时间
		expire(sessionId);
	}

	/**
	 * 设置 Session（Redis） 中的值（自定义过期时间，不会跟随用户使用系统更新）
	 * @param key 键
	 * @param value 值
	 * @param timeout 过期时间
	 * @param unit 过期时间单位
	 */
	@Override
	public void setAttr(@NonNull String key, @NonNull String value, long timeout, @NonNull TimeUnit unit) {

		String sessionId = sessionId();
		stringRedisTemplate.opsForValue().set(sessionId + ":" + key, value, timeout, unit);

	}

	/**
	 * 获取 Session（Redis） 中的值
	 * @param key 键
	 * @return 返回 Session（Redis） 中的值
	 */
	@Override
	public String getAttr(@NonNull String key) {

		String sessionId = sessionId();
		return stringRedisTemplate.opsForValue().get(sessionId + ":" + key);
	}

	/**
	 * 设置 Redis 中的值（自定义过期时间，不会跟随用户使用系统更新）
	 * @param key 键
	 * @param value 值
	 * @param timeout 过期时间
	 * @param unit 过期时间单位
	 */
	@Override
	public void set(@NonNull String key, @NonNull String value, long timeout, @NonNull TimeUnit unit) {
		stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
	}

	/**
	 * 获取 Redis 中的值
	 * @param key 键
	 * @return 返回 Redis 中的值
	 */
	@Override
	public String get(@NonNull String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	/**
	 * 获取 Session（Redis） 中的值
	 * @param key 键
	 * @return 返回 值
	 */
	@Override
	public Object getAttribute(@NonNull String key) {
		String sessionId = sessionId();
		// 过期时间
		expire(sessionId);
		return redisTemplate.opsForHash().get(sessionId, key);
	}

	/**
	 * 移除 Session（Redis） 中的值
	 * @param key 键
	 */
	@Override
	public void removeAttribute(@NonNull String key) {
		String sessionId = sessionId();
		redisTemplate.opsForHash().delete(sessionId, key);
	}

	/**
	 * 移除 Session（Redis）
	 * @param key 键
	 */
	@Override
	public void remove(@NonNull String key) {
		String sessionId = sessionId();
		redisTemplate.delete(sessionId);
		// 过期时间
		expire(sessionId);
	}

	/**
	 * 设置过期时间
	 * @param sessionId Session ID
	 */
	private void expire(String sessionId) {
		long timeout = cloudSessionProperties.getTimeout();
		TimeUnit unit = cloudSessionProperties.getUnit();

		// 过期时间
		redisTemplate.expire(sessionId, timeout, unit);
	}

}
