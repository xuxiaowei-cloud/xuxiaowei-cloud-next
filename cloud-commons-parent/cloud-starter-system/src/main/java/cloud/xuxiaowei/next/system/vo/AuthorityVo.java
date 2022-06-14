package cloud.xuxiaowei.next.system.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 权限与权限说明表
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-05-17
 */
@Data
public class AuthorityVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限
     */
    private String authority;

    /**
     * 权限说明
     */
    private String explain;


}
