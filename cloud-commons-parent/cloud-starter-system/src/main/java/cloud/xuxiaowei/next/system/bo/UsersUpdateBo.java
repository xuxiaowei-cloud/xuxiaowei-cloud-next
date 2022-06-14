package cloud.xuxiaowei.next.system.bo;

import cloud.xuxiaowei.next.system.annotation.UsersIdAnnotation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * 用户表
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class UsersUpdateBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键，自增
     */
    @UsersIdAnnotation
    @NotNull(message = "用户主键 不能为空")
    private Long usersId;

    /**
     * 邮箱，唯一键：uk__users__email
     */
    @Email(message = "邮箱 不合法")
    private String email;

    /**
     * 邮箱是否验证，不为空，默认值：0
     */
    @NotNull(message = "邮箱是否验证 不能为空")
    private Boolean emailValid;

    /**
     * 昵称，不能为空，唯一键：uk__users__nickname
     */
    @Length(min = 2, max = 10, message = "昵称 长度限制：2-10")
    @NotEmpty(message = "昵称 不能为空")
    private String nickname;

    /**
     * 密码，不能为空
     */
    private String password;

    /**
     * 用户识别码
     */
    @NotEmpty(message = "用户识别码 不能为空")
    private String code;

    /**
     * 是否启用，不能为空
     */
    @NotNull(message = "是否启用 不能为空")
    private Boolean enabled;

    /**
     * 帐户未过期，不能为空
     */
    @NotNull(message = "帐户未过期 不能为空")
    private Boolean accountNonExpired;

    /**
     * 凭证未过期，不能为空
     */
    @NotNull(message = "凭证未过期 不能为空")
    private Boolean credentialsNonExpired;

    /**
     * 帐户未锁定，不能为空
     */
    @NotNull(message = "帐户未锁定 不能为空")
    private Boolean accountNonLocked;

}
