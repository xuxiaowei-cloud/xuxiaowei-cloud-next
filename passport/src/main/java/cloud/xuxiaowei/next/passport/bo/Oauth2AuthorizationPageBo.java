package cloud.xuxiaowei.next.passport.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 授权表 分页参数
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class Oauth2AuthorizationPageBo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long current;

    private Long size;

    private String registeredClientId;

    private String principalName;

}
