package cloud.xuxiaowei.next.masterdata.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

import static cloud.xuxiaowei.next.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 字典数据表
 * <p>
 * 为何本表不使用自增主键？
 * <p>
 * 答：考虑到开发环境添加数据、正式环境添加数据、开发环境向正式环境同步字典数据等情况，相同的字典代码与字典数据代码可能对应不同的自增主键，故放弃自增主键，改用字典代码与字典数据代码作为联合主键。
 * <p>
 * 本表external_code与external_label开头的字段有何作用？
 * <p>
 * 答：对接外部系统时，如果外部系统与本系统针对某一字典值、名称不同时，在此增加两列做对照，并在字段中在增加对外部系统的描述，使用时，直接取不同系统对照的不同字段即可
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-23
 */
@Data
@TableName("dict_data")
public class DictData implements Serializable {

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

	/**
	 * 创建人，不为空
	 */
	@TableField(fill = FieldFill.INSERT)
	private String createUsersId;

	/**
	 * 创建时间，不为空，数据库自动生成
	 */
	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createDate;

	/**
	 * 创建者IP，不为空
	 */
	@TableField(fill = FieldFill.INSERT)
	private String createIp;

	/**
	 * 更新人，未更新时为空
	 */
	@TableField(fill = FieldFill.UPDATE)
	private String updateUsersId;

	/**
	 * 更新时间，未更新时为空
	 */
	@JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateDate;

	/**
	 * 更新者IP，未更新时为空
	 */
	@TableField(fill = FieldFill.UPDATE)
	private String updateIp;

	/**
	 * 逻辑删除，0 未删除，1 删除，MySQL 默认值 0，不为 NULL，注解@TableLogic。
	 */
	@TableLogic
	private Boolean deleted;

}
