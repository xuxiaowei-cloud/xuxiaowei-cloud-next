package cloud.xuxiaowei.next.passport.bo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

/**
 * 授权同意书表 联合主键
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class Oauth2AuthorizationConsentPrimaryKey implements Serializable {

	private static final long serialVersionUID = 1L;

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
