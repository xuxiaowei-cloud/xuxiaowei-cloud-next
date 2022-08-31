package cloud.xuxiaowei.next.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 微信小程序用户
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-05-15
 */
@Data
@TableName("wx_ma_users")
public class WxMaUsers implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 微信小程序用户主键ID，自增
	 */
	@TableId(value = "wx_ma_users_id", type = IdType.AUTO)
	private Long wxMaUsersId;

	/**
	 * 小程序标识，不为空，唯一键：uk__wx_ma_users__appid__openid
	 */
	private String appid;

	/**
	 * 用户标识（针对于某个小程序），不为空，唯一键：uk__wx_ma_users__appid__openid
	 */
	private String openid;

	/**
	 * 用户标识（针对于同一开放平台）
	 */
	private String unionid;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建时间，不为空
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createDate;

	/**
	 * 更新时间，未更新时为空
	 */
	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateDate;

	/**
	 * 创建人，不为空
	 */
	@TableField(fill = FieldFill.INSERT)
	private String createUsersId;

	/**
	 * 更新人，未更新时为空
	 */
	@TableField(fill = FieldFill.UPDATE)
	private String updateUsersId;

	/**
	 * 创建者IP，不为空
	 */
	@TableField(fill = FieldFill.INSERT)
	private String createIp;

	/**
	 * 更新者IP，未更新时为空
	 */
	@TableField(fill = FieldFill.UPDATE)
	private String updateIp;

	/**
	 * 逻辑删除，0 未删除，1 删除，MySQL 默认值 0，不为 NULL，注解@TableLogic。
	 */
	@TableLogic
	private Boolean deleted;

}
