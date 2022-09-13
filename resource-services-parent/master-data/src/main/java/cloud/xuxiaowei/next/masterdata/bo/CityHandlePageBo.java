package cloud.xuxiaowei.next.masterdata.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 城市分页参数
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class CityHandlePageBo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long current;

	/**
	 * 省份管辖下，最多有21个城市
	 */
	private Long size;

	/**
	 * 省份代码
	 */
	private Integer provinceCode;

	/**
	 * 城市代码，唯一键：uk__city_handle__city_code
	 */
	private Integer cityCode;

	/**
	 * 城市名称
	 */
	private String cityName;

}
