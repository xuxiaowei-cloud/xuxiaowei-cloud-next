package cloud.xuxiaowei.next.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static cloud.xuxiaowei.next.utils.DateUtils.DEFAULT_DATE_FORMAT;
import static cloud.xuxiaowei.next.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * 用户
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class UsersVo implements Serializable {

	private static final long serialVersionUID = 1L;

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

	/**
	 * 性别，取表：dict_data.dict_code = 'sex'
	 */
	private String sex;

	/**
	 * 性别展示
	 */
	private String sexLabel;

	/**
	 * 性别说明
	 */
	private String sexExplain;

	/**
	 * 生日
	 */
	@JsonFormat(pattern = DEFAULT_DATE_FORMAT)
	private LocalDate birthday;

	/**
	 * 省代码，取表：province_handle.province_code
	 */
	private Integer provinceCode;

	private String provinceName;

	/**
	 * 市代码，取表：city_handle.city_code
	 */
	private Integer cityCode;

	private String cityName;

	/**
	 * 区/县代码，取表：county_handle.county_code
	 */
	private Integer countyCode;

	private String countyName;

	/**
	 * 镇代码，取表：town_handle.town_code
	 */
	private Integer townCode;

	private String townName;

	/**
	 * 居委会代码，取表：village_handle.town_code
	 */
	private Long villageCode;

	private String villageName;

	/**
	 * 详细地址
	 */
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
	 * 创建时间，不为空，数据库自动生成
	 */
	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	private LocalDateTime createDate;

	/**
	 * 更新时间，未更新时为空
	 */
	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	private LocalDateTime updateDate;

	/**
	 * 权限
	 */
	private Set<AuthorityVo> authorityList;

}
