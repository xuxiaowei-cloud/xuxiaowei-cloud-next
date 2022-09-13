package cloud.xuxiaowei.next.masterdata.bo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 更新字典表参数
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictUpdateBo extends DictBo {

	private static final long serialVersionUID = 1L;

	/**
	 * 字典代码
	 */
	@NotEmpty(message = "字典代码 不能为空")
	private String dictCode;

}
