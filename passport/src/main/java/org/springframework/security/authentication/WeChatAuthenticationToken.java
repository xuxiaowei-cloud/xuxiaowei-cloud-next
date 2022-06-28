package org.springframework.security.authentication;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * 微信用户 登录认证令牌
 *
 * @author Ben Alex
 * @author Norbert Nowak
 * @author xuxiaowei
 */
public class WeChatAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	/**
	 * 微信小程序的appid，不能为空
	 */
	@Getter
	private final String appid;

	/**
	 * 用户唯一标识，不能为空
	 */
	@Getter
	private final String openid;

	/**
	 * 用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回，详见 <a href=
	 * "https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/union-id.html">UnionID
	 * 机制说明</a>。
	 */
	@Getter
	private final String unionid;

	/**
	 * 会话密钥
	 */
	@Getter
	private final String sessionKey;

	private final Object principal;

	private Object credentials;

	/**
	 * This constructor can be safely used by any code that wishes to create a
	 * <code>WeChatAuthenticationToken</code>, as the {@link #isAuthenticated()} will
	 * return <code>false</code>.
	 *
	 */
	public WeChatAuthenticationToken(Object principal, Object credentials, String appid, String openid, String unionid,
			String sessionKey) {
		super(null);
		Assert.notNull(appid, "appid 不能为空");
		Assert.notNull(openid, "appid 不能为空");
		this.principal = principal;
		this.credentials = credentials;
		this.appid = appid;
		this.openid = openid;
		this.unionid = unionid;
		this.sessionKey = sessionKey;
		setAuthenticated(false);
	}

	/**
	 * This constructor should only be used by <code>AuthenticationManager</code> or
	 * <code>AuthenticationProvider</code> implementations that are satisfied with
	 * producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
	 * authentication token.
	 * @param principal
	 * @param credentials
	 * @param authorities
	 */
	public WeChatAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities, String appid, String openid, String unionid,
			String sessionKey) {
		super(authorities);
		Assert.notNull(appid, "appid 不能为空");
		Assert.notNull(openid, "appid 不能为空");
		this.principal = principal;
		this.credentials = credentials;
		this.appid = appid;
		this.openid = openid;
		this.unionid = unionid;
		this.sessionKey = sessionKey;
		// must use super, as we override
		super.setAuthenticated(true);
	}

	/**
	 * This factory method can be safely used by any code that wishes to create a
	 * unauthenticated <code>WeChatAuthenticationToken</code>.
	 * @param principal
	 * @param credentials
	 * @return WeChatAuthenticationToken with false isAuthenticated() result
	 *
	 * @since 5.7
	 */
	public static WeChatAuthenticationToken unauthenticated(Object principal, Object credentials, String appid,
			String openid, String unionid, String sessionKey) {
		return new WeChatAuthenticationToken(principal, credentials, appid, openid, unionid, sessionKey);
	}

	/**
	 * This factory method can be safely used by any code that wishes to create a
	 * authenticated <code>WeChatAuthenticationToken</code>.
	 * @param principal
	 * @param credentials
	 * @return WeChatAuthenticationToken with true isAuthenticated() result
	 *
	 * @since 5.7
	 */
	public static WeChatAuthenticationToken authenticated(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities, String appid, String openid, String unionid,
			String sessionKey) {
		return new WeChatAuthenticationToken(principal, credentials, authorities, appid, openid, unionid, sessionKey);
	}

	@Override
	public Object getCredentials() {
		return this.credentials;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		Assert.isTrue(!isAuthenticated,
				"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		super.setAuthenticated(false);
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		this.credentials = null;
	}

}
