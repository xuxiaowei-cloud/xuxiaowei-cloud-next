package cloud.xuxiaowei.next.masterdata.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 城市
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-20
 */
@Data
@TableName("city_handle")
public class CityHandle implements Serializable {

	private static final long serialVersionUID = 1L;

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
