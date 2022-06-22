package cloud.xuxiaowei.next.passport.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

import static cloud.xuxiaowei.next.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * 授权表
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class Oauth2AuthorizationVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String registeredClientId;

	private String principalName;

	private String authorizationGrantType;

	private String attributes;

	private String state;

	private String authorizationCodeValue;

	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	private LocalDateTime authorizationCodeIssuedAt;

	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	private LocalDateTime authorizationCodeExpiresAt;

	private String authorizationCodeMetadata;

	private String accessTokenValue;

	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	private LocalDateTime accessTokenIssuedAt;

	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	private LocalDateTime accessTokenExpiresAt;

	private String accessTokenMetadata;

	private String accessTokenType;

	private String accessTokenScopes;

	private String oidcIdTokenValue;

	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	private LocalDateTime oidcIdTokenIssuedAt;

	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	private LocalDateTime oidcIdTokenExpiresAt;

	private String oidcIdTokenMetadata;

	private String refreshTokenValue;

	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	private LocalDateTime refreshTokenIssuedAt;

	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	private LocalDateTime refreshTokenExpiresAt;

	private String refreshTokenMetadata;

}
