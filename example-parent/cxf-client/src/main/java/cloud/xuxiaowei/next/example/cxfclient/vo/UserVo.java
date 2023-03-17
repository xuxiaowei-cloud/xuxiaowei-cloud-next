package cloud.xuxiaowei.next.example.cxfclient.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class UserVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String username;

	private String password;

}
