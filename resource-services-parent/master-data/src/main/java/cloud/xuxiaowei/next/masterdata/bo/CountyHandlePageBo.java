package cloud.xuxiaowei.next.masterdata.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 县分页参数
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class CountyHandlePageBo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long current;

	/**
	 * 城市管辖下，最多有26个县
	 */
	private Long size;

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
