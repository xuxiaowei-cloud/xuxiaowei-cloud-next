package cloud.xuxiaowei.next.system.bo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 权限表保存参数
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class AuthoritiesSaveBo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户名
	 */
	@NotEmpty(message = "用户名 不能为空")
	private String username;

	/**
	 * 权限
	 */
	private Set<String> authorityList;

}
