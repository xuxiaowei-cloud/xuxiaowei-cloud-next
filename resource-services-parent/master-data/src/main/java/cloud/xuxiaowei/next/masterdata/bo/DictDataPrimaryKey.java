package cloud.xuxiaowei.next.masterdata.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典数据表联合主键
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class DictDataPrimaryKey implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 字典代码，联合主键，取表：dict.dict_code
	 */
	@NotNull(message = "字典代码 不能为空")
	private String dictCode;

	/**
	 * 字典数据代码，联合主键
	 */
	@NotNull(message = "字典数据代码 不能为空")
	private String dictDataCode;

}
