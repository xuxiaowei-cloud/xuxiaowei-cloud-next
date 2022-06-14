package cloud.xuxiaowei.next.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import static cloud.xuxiaowei.next.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * 用户
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
public class UsersVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键，自增
     */
    private Long usersId;

    /**
     * 用户名，不能为空，唯一键：uk__users__username
     */
    private String username;

    /**
     * 邮箱，唯一键：uk__users__email
     */
    private String email;

    /**
     * 邮箱是否验证，不为空，默认值：0
     */
    private Boolean emailValid;

    /**
     * 昵称，不能为空，唯一键：uk__users__nickname
     */
    private String nickname;

    /**
     * 是否启用，不能为空
     */
    private Boolean enabled;

    /**
     * 帐户未过期，不能为空
     */
    private Boolean accountNonExpired;

    /**
     * 凭证未过期，不能为空
     */
    private Boolean credentialsNonExpired;

    /**
     * 帐户未锁定，不能为空
     */
    private Boolean accountNonLocked;

    /**
     * 创建时间，不为空，数据库自动生成
     */
    @JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
    private LocalDateTime createDate;

    /**
     * 更新时间，未更新时为空
     */
    @JsonFormat(pattern = DEFAULT_DATE_TIME_FORMAT)
    private LocalDateTime updateDate;

    /**
     * 权限
     */
    private Set<AuthorityVo> authorityList;

}
