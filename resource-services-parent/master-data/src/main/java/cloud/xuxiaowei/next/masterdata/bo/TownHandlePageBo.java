package cloud.xuxiaowei.next.masterdata.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 镇分页参数
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class TownHandlePageBo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long current;

	/**
	 * 县管辖下，最多有52个镇
	 */
	private Long size;

	/**
	 * 县代码
	 */
	private Integer countyCode;

	/**
	 * 镇代码，唯一键：uk__town_handle__town_code
	 */
	private Integer townCode;

	/**
	 * 镇名称
	 */
	private String townName;

}
