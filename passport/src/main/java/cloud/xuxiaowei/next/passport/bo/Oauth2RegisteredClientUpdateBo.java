package cloud.xuxiaowei.next.passport.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.time.LocalDateTime;

import static cloud.xuxiaowei.next.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 客户表 更新参数
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class Oauth2RegisteredClientUpdateBo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String clientId;

	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	private LocalDateTime clientIdIssuedAt;

	private String clientSecret;

	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	private LocalDateTime clientSecretExpiresAt;

	/**
	 * 用户识别码
	 */
	@NotEmpty(message = "用户识别码 不能为空")
	private String code;

	@NotEmpty(message = "clientName 不能为空")
	private String clientName;

	@NotEmpty(message = "clientAuthenticationMethods 不能为空")
	private String clientAuthenticationMethods;

	private String authorizationGrantTypes;

	private String redirectUris;

	@NotEmpty(message = "scopes 不能为空")
	private String scopes;

	@NotNull(message = "requireProofKey 不能为空")
	private Boolean requireProofKey;

	@NotNull(message = "requireAuthorizationConsent 不能为空")
	private Boolean requireAuthorizationConsent;

	@NotNull(message = "authorizationCodeTimeToLive 不能为空")
	private Long authorizationCodeTimeToLive;

	@NotNull(message = "accessTokenTimeToLive 不能为空")
	private Long accessTokenTimeToLive;

	@NotNull(message = "refreshTokenTimeToLive 不能为空")
	private Long refreshTokenTimeToLive;

	private String tokenSigningAlgorithm;

	private String tokenSignatureAlgorithm;

	@NotNull(message = "jwkSetUrl 不能为空")
	@URL(message = "jwkSetUrl 不合法")
	private String jwkSetUrl;

	private Boolean reuseRefreshTokens;

	private String accessTokenFormat;

}
