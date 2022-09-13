package cloud.xuxiaowei.next.passport.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

import static cloud.xuxiaowei.next.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 客户表。
 * 原表结构：oauth2-authorization-server-*.*.*.jar!/org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql
 * 原表结构：https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql
 * GitCode
 * 镜像仓库：https://gitcode.net/mirrors/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-06-18
 */
@Data
@TableName("oauth2_registered_client")
public class Oauth2RegisteredClient implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	private String clientId;

	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	private LocalDateTime clientIdIssuedAt;

	private String clientSecret;

	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	private LocalDateTime clientSecretExpiresAt;

	private String clientName;

	private String clientAuthenticationMethods;

	private String authorizationGrantTypes;

	private String redirectUris;

	private String scopes;

	private String clientSettings;

	private String tokenSettings;

}
