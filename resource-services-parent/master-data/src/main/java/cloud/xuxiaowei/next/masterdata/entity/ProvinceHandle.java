package cloud.xuxiaowei.next.masterdata.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 省份
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-20
 */
@Data
@TableName("province_handle")
public class ProvinceHandle implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 年份
	 */
	@TableField("`year`")
	private Integer year;

	/**
	 * 省份代码，唯一键：uk__province_handle__province_code
	 */
	private Integer provinceCode;

	/**
	 * 省份名称
	 */
	private String provinceName;

}
