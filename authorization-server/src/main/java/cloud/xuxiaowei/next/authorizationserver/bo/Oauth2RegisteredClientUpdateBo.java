package cloud.xuxiaowei.next.authorizationserver.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

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

    private String clientName;

    private String clientAuthenticationMethods;

    private String authorizationGrantTypes;

    private String redirectUris;

    private String scopes;

    private String clientSettings;

    private String tokenSettings;


}
