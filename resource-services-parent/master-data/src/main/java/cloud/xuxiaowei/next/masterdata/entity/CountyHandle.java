package cloud.xuxiaowei.next.masterdata.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 县
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-20
 */
@Data
@TableName("county_handle")
public class CountyHandle implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 城市代码
	 */
	private Integer cityCode;

	/**
	 * 县代码，唯一键：uk__county_handle__county_code
	 */
	private Integer countyCode;

	/**
	 * 县名称
	 */
	private String countyName;

}
