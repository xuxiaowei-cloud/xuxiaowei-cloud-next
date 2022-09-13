package cloud.xuxiaowei.next.masterdata.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 居委会
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-20
 */
@Data
@TableName("village_handle")
public class VillageHandle implements Serializable {

	private static final long serialVersionUID = 1L;

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
