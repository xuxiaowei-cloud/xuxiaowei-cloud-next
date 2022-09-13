package cloud.xuxiaowei.next.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static cloud.xuxiaowei.next.utils.DateUtils.DEFAULT_DATE_FORMAT;
import static cloud.xuxiaowei.next.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.IGNORED;

/**
 * <p>
 * 用户表。
 * 原表结构：spring-security-core-*.*.*.jar!/org/springframework/security/core/userdetails/jdbc/users.ddl
 * 原表结构：https://github.com/spring-projects/spring-security/blob/main/core/src/main/resources/org/springframework/security/core/userdetails/jdbc/users.ddl
 * GitCode
 * 镜像仓库：https://gitcode.net/mirrors/spring-projects/spring-security/blob/main/core/src/main/resources/org/springframework/security/core/userdetails/jdbc/users.ddl
 * Gitee
 * 镜像仓库：https://gitee.com/mirrors/spring-security/blob/main/core/src/main/resources/org/springframework/security/core/userdetails/jdbc/users.ddl
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-04-06
 */
@Data
public class Users implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户主键，自增
	 */
	@TableId(value = "users_id", type = IdType.AUTO)
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

	/**
	 * 密码，不能为空
	 */
	@TableField("`password`")
	private String password;

	/**
	 * 性别，取表：dict_data.dict_code = 'sex'
	 */
	private String sex;

	/**
	 * 性别展示
	 */
	@TableField(exist = false)
	private String sexLabel;

	/**
	 * 性别说明
	 */
	@TableField(exist = false)
	private String sexExplain;

	/**
	 * 生日
	 */
	@JsonFormat(pattern = DEFAULT_DATE_FORMAT)
	private LocalDate birthday;

	/**
	 * 省代码，取表：province_handle.province_code
	 */
	@TableField(updateStrategy = IGNORED)
	private Integer provinceCode;

	@TableField(exist = false)
	private String provinceName;

	/**
	 * 市代码，取表：city_handle.city_code
	 */
	@TableField(updateStrategy = IGNORED)
	private Integer cityCode;

	@TableField(exist = false)
	private String cityName;

	/**
	 * 区/县代码，取表：county_handle.county_code
	 */
	@TableField(updateStrategy = IGNORED)
	private Integer countyCode;

	@TableField(exist = false)
	private String countyName;

	/**
	 * 镇代码，取表：town_handle.town_code
	 */
	@TableField(updateStrategy = IGNORED)
	private Integer townCode;

	@TableField(exist = false)
	private String townName;

	/**
	 * 居委会代码，取表：village_handle.town_code
	 */
	@TableField(updateStrategy = IGNORED)
	private Long villageCode;

	@TableField(exist = false)
	private String villageName;

	/**
	 * 详细地址
	 */
	@TableField(updateStrategy = IGNORED)
	private String detailAddress;

	/**
	 * 是否启用，不能为空
	 */
	private Boolean enabled;

	/**
	 * 帐户未过期，不能为空
	 */
	private Boolean accountNonExpired;

	/**
	 * 凭证未过期，不能为空
	 */
	private Boolean credentialsNonExpired;

	/**
	 * 帐户未锁定，不能为空
	 */
	private Boolean accountNonLocked;

	/**
	 * 更新时间，未更新时为空
	 */
	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateDate;

	/**
	 * 创建时间，不为空，数据库自动生成
	 */
	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createDate;

	/**
	 * 逻辑删除，0 未删除，1 删除，MySQL 默认值 0，不为 NULL，注解@TableLogic。
	 */
	@TableLogic
	private Boolean deleted;

	/**
	 * 权限
	 */
	@TableField(exist = false)
	private List<Authorities> authoritiesList;

}
