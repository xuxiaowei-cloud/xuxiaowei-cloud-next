package cloud.xuxiaowei.next.masterdata.bo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典表
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class DictBo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 字典说明，不为空
	 */
	@NotEmpty(message = "字典说明 不能为空")
	private String dictExplain;

	/**
	 * Redis失效时间，不为空，单位：秒，小于等于0代表永不过期，不推荐
	 */
	@NotNull(message = "Redis失效时间 不能为空")
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
