package cloud.xuxiaowei.next.masterdata.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 区域分页参数
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class RegionPageBo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 是否精确匹配
	 */
	private boolean type;

	private Long current;

	private Long size;

	/**
	 * 省份代码，唯一键：uk__province_handle__province_code
	 */
	private Integer provinceCode;

	/**
	 * 省份名称
	 */
	private String provinceName;

	/**
	 * 城市代码，唯一键：uk__city_handle__city_code
	 */
	private Integer cityCode;

	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 县代码，唯一键：uk__county_handle__county_code
	 */
	private Integer countyCode;

	/**
	 * 县名称
	 */
	private String countyName;

	/**
	 * 镇代码，唯一键：uk__town_handle__town_code
	 */
	private Integer townCode;

	/**
	 * 镇名称
	 */
	private String townName;

	/**
	 * 居委会代码，唯一键：uk__village_handle__village_code
	 */
	private Long villageCode;

	/**
	 * 居委会名称
	 */
	private String villageName;

	/**
	 * 居委会类型代码
	 */
	private String villageTypeCode;

}
