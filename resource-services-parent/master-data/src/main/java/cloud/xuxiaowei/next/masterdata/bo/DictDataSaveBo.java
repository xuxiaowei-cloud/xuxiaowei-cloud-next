package cloud.xuxiaowei.next.masterdata.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据表保存参数
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictDataSaveBo extends DictDataBo {

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
