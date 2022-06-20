package cloud.xuxiaowei.next.authorizationserver.bo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 授权同意书表 联合主键
 *
 * @since 0.0.1
 * @author xuxiaowei
 */
@Data
public class Oauth2AuthorizationConsentPrimaryKey {

    /**
     * 客户主键
     */
    @NotEmpty(message = "客户主键 不能为空")
    private String registeredClientId;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名 不能为空")
    private String principalName;

}
