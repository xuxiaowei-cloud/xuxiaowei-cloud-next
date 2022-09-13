package cloud.xuxiaowei.next.masterdata.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

import static cloud.xuxiaowei.next.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-23
 */
@Data
public class Dict implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 字典代码，主键
	 * <p>
	 * 为何本表不使用自增主键？
	 * <p>
	 * 答：考虑到开发环境添加数据、正式环境添加数据、开发环境向正式环境同步字典数据等情况，相同的字典代码可能对应不同的自增主键，故放弃自增主键，改用字典代码作为主键。
	 */
	@TableId(type = IdType.INPUT)
	private String dictCode;

	/**
	 * 字典说明，不为空
	 */
	private String dictExplain;

	/**
	 * Redis失效时间，不为空，单位：秒，小于等于0代表永不过期，不推荐
	 */
	private Integer redisExpire;

	/**
	 * 国标代码
	 */
	private String gb;

	/**
	 * 国标地址
	 */
	private String gbUrl;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建人，不为空
	 */
	@TableField(fill = FieldFill.INSERT)
	private String createUsersId;

	/**
	 * 创建时间，不为空，数据库自动生成
	 */
	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createDate;

	/**
	 * 创建者IP，不为空
	 */
	@TableField(fill = FieldFill.INSERT)
	private String createIp;

	/**
	 * 更新人，未更新时为空
	 */
	@TableField(fill = FieldFill.UPDATE)
	private String updateUsersId;

	/**
	 * 更新时间，未更新时为空
	 */
	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateDate;

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
