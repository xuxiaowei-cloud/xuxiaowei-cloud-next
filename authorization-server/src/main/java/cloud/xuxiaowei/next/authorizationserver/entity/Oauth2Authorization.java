package cloud.xuxiaowei.next.authorizationserver.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 授权表。	原表结构：oauth2-authorization-server-*.*.*.jar!/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql	原表结构：https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql	GitCode 镜像仓库：https://gitcode.net/mirrors/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-06-18
 */
@Data
@TableName("oauth2_authorization")
public class Oauth2Authorization implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String registeredClientId;

    private String principalName;

    private String authorizationGrantType;

    private String attributes;

    private String state;

    private String authorizationCodeValue;

    private LocalDateTime authorizationCodeIssuedAt;

    private LocalDateTime authorizationCodeExpiresAt;

    private String authorizationCodeMetadata;

    private String accessTokenValue;

    private LocalDateTime accessTokenIssuedAt;

    private LocalDateTime accessTokenExpiresAt;

    private String accessTokenMetadata;

    private String accessTokenType;

    private String accessTokenScopes;

    private String oidcIdTokenValue;

    private LocalDateTime oidcIdTokenIssuedAt;

    private LocalDateTime oidcIdTokenExpiresAt;

    private String oidcIdTokenMetadata;

    private String refreshTokenValue;

    private LocalDateTime refreshTokenIssuedAt;

    private LocalDateTime refreshTokenExpiresAt;

    private String refreshTokenMetadata;


}
