package cloud.xuxiaowei.next.passport.authentication;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientCredentialsAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2RefreshTokenAuthenticationToken;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * 微信 OAuth2 用于验证授权授予的 。
 *
 * @author xuxiaowei
 * @since 0.0.1
 * @see OAuth2AuthorizationCodeAuthenticationToken 用于 OAuth 2.0 授权代码授予的
 * {@link Authentication} 实现。
 * @see OAuth2RefreshTokenAuthenticationToken 用于 OAuth 2.0 刷新令牌授予的 {@link Authentication}
 * 实现。
 * @see OAuth2ClientCredentialsAuthenticationToken 用于 OAuth 2.0 客户端凭据授予的
 * {@link Authentication} 实现。
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class OAuth2WeChatAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

	public static final AuthorizationGrantType WECHAT = new AuthorizationGrantType("wechat");

	@Getter
	private final String openid;

	/**
	 * 子类构造函数
	 * @param clientPrincipal 经过身份验证的客户端主体
	 * @param additionalParameters 附加参数
	 */
	public OAuth2WeChatAuthenticationToken(String openid, Authentication clientPrincipal,
			Map<String, Object> additionalParameters) {
		super(WECHAT, clientPrincipal, additionalParameters);
		Assert.hasText(openid, "openid 不能为空");
		this.openid = openid;
	}

}
