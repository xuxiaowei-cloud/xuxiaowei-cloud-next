package cloud.xuxiaowei.next.system.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限与权限说明分页参数
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AuthorityPageBo extends AuthorityBo {

	private static final long serialVersionUID = 1L;

	private Long current;

	private Long size;

}
