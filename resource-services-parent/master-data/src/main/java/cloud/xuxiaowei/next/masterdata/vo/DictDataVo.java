package cloud.xuxiaowei.next.masterdata.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 字典数据
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class DictDataVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 字典代码，联合主键，取表：dict.dict_code
	 */
	private String dictCode;

	/**
	 * 字典数据代码，联合主键
	 */
	private String dictDataCode;

	/**
	 * 字典数据名称（展示），不为空
	 */
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
