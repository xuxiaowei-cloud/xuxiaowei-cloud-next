package cloud.xuxiaowei.next.passport.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 授权类型
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrantTypeOption implements Serializable {

	private String value;

	private String label;

}
