package cloud.xuxiaowei.next.masterdata.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 居委会分页参数
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class VillageHandlePageBo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long current;

	/**
	 * 镇管辖下，最多有168居委会
	 */
	private Long size;

	/**
	 * 镇代码
	 */
	private Integer townCode;

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
