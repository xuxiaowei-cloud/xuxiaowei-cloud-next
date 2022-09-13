package cloud.xuxiaowei.next.masterdata.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 字典分页参数
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class DictPageBo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long current;

	private Long size;

	/**
	 * 字典代码，主键
	 * <p>
	 * 为何本表不使用自增主键？
	 * <p>
	 * 答：考虑到开发环境添加数据、正式环境添加数据、开发环境向正式环境同步字典数据等情况，相同的字典代码可能对应不同的自增主键，故放弃自增主键，改用字典代码作为主键。
	 */
	private String dictCode;

	/**
	 * 字典说明，不为空
	 */
	private String dictExplain;

	/**
	 * Redis失效时间，不为空，单位：秒，小于等于0代表永不过期，不推荐
	 */
	private Integer redisExpire;

	/**
	 * 国标代码
	 */
	private String gb;

	/**
	 * 国标地址
	 */
	private String gbUrl;

	/**
	 * 备注
	 */
	private String remark;

}
