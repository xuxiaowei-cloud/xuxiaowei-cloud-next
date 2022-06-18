package cloud.xuxiaowei.next.authorizationserver.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 客户表 分页参数
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class Oauth2RegisteredClientPageBo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long current;

    private Long size;

    private String id;

    private String clientId;

    private String clientName;

}
