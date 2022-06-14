package cloud.xuxiaowei.next.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 权限表。	原表结构：spring-security-core-*.*.*.jar!/org/springframework/security/core/userdetails/jdbc/users.ddl	原表结构：https://github.com/spring-projects/spring-security/blob/main/core/src/main/resources/org/springframework/security/core/userdetails/jdbc/users.ddl	GitCode 镜像仓库：https://gitcode.net/mirrors/spring-projects/spring-security/blob/main/core/src/main/resources/org/springframework/security/core/userdetails/jdbc/users.ddl	Gitee 镜像仓库：https://gitee.com/mirrors/spring-security/blob/main/core/src/main/resources/org/springframework/security/core/userdetails/jdbc/users.ddl
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-04-06
 */
@Data
public class Authorities implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String authority;

    @TableField(exist = false)
    private String explain;

    /**
     * 更新时间，未更新时为空
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateDate;

    /**
     * 创建时间，不为空，数据库自动生成
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;


}
