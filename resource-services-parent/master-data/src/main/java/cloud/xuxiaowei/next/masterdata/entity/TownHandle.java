package cloud.xuxiaowei.next.masterdata.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 镇
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-20
 */
@Data
@TableName("town_handle")
public class TownHandle implements Serializable {

	private static final long serialVersionUID = 1L;

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
