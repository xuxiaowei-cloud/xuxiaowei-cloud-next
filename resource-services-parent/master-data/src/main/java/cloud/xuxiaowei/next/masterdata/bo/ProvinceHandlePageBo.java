package cloud.xuxiaowei.next.masterdata.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 省份分页参数
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class ProvinceHandlePageBo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long current;

	/**
	 * 一共31个省
	 */
	private Long size;

	/**
	 * 省份代码，唯一键：uk__province_handle__province_code
	 */
	private Integer provinceCode;

	/**
	 * 省份名称
	 */
	private String provinceName;

}
