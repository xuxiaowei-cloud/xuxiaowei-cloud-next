package cloud.xuxiaowei.next.masterdata.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典数据表
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class DictDataBo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 字典数据名称（展示），不为空
	 */
	@NotNull(message = "字典数据名称 不能为空")
	private String dictDataLabel;

	/**
	 * 字典数据排序
	 */
	private Integer dictDataSort;

	/**
	 * 字典数据说明
	 */
	private String dictDataExplain;

	/**
	 * 外部XX系统代码1：
	 * <p>
	 * 对接外部系统时，如果外部系统与本系统针对某一字典值不同时，在此增加一列做对照
	 */
	private String externalCodeOne;

	/**
	 * 外部XX系统名称1：
	 * <p>
	 * 对接外部系统时，如果外部系统与本系统针对某一字典值对应的名称不同时，在此增加一列做对照
	 */
	private String externalLabelOne;

	/**
	 * 备注
	 */
	private String remark;

}
