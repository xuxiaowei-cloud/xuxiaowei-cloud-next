package cloud.xuxiaowei.next.system.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDate;

import static cloud.xuxiaowei.next.utils.DateUtils.DEFAULT_DATE_FORMAT;

/**
 * 用户表
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class UsersUpdateBo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 昵称，不能为空，唯一键：uk__users__nickname
	 */
	@Length(min = 2, max = 10, message = "昵称 长度限制：2-10")
	@NotEmpty(message = "昵称 不能为空")
	private String nickname;

	/**
	 * 性别，取表：dict_data.dict_code = 'sex'
	 */
	@NotEmpty(message = "性别 不能为空")
	private String sex;

	/**
	 * 生日
	 */
	@NotNull(message = "生日 不能为空")
	@JsonFormat(pattern = DEFAULT_DATE_FORMAT)
	private LocalDate birthday;

	/**
	 * 省代码，取表：province_handle.province_code
	 */
	private Integer provinceCode;

	/**
	 * 市代码，取表：city_handle.city_code
	 */
	private Integer cityCode;

	/**
	 * 区/县代码，取表：county_handle.county_code
	 */
	private Integer countyCode;

	/**
	 * 镇代码，取表：town_handle.town_code
	 */
	private Integer townCode;

	/**
	 * 居委会代码，取表：village_handle.town_code
	 */
	private Long villageCode;

	/**
	 * 详细地址
	 */
	private String detailAddress;

}
