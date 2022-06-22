package cloud.xuxiaowei.next.system.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 管理用户 分页参数
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class ManageUsersPageBo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long current;

	private Long size;

	/**
	 * 用户主键，自增
	 */
	private Long usersId;

	/**
	 * 用户名，不能为空，唯一键：uk__users__username
	 */
	private String username;

	/**
	 * 邮箱，唯一键：uk__users__email
	 */
	private String email;

	/**
	 * 邮箱是否验证，不为空，默认值：0
	 */
	private Boolean emailValid;

	/**
	 * 昵称，不能为空，唯一键：uk__users__nickname
	 */
	private String nickname;

}
