package cloud.xuxiaowei.next.system.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 权限与权限说明参数
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class AuthorityBo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String authority;

    private String explain;

}
