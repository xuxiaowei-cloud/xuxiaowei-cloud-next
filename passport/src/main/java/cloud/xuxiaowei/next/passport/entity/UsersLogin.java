package cloud.xuxiaowei.next.passport.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户登录表
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-06-09
 */
@Data
@TableName("users_login")
public class UsersLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户登录主键，自增
     */
    @TableId(value = "users_login_id", type = IdType.AUTO)
    private Long usersLoginId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 是否成功，不为空
     */
    private Boolean success;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 参数
     */
    private String queryString;

    /**
     * 请求头，json
     */
    private String headersMap;

    /**
     * 标识
     */
    private String userAgent;

    /**
     * 请求ID，不为空
     */
    private String requestId;

    /**
     * Session ID
     */
    private String sessionId;

    /**
     * IP，不为空
     */
    private String ip;

    /**
     * 异常
     */
    private String exception;

    /**
     * browscap 是否处理
     */
    private Boolean browscap;

    /**
     * 浏览器值（例如 Chrome）
     */
    private String browser;

    /**
     * 浏览器类型（例如 Browser 或 Application）
     */
    private String browserType;

    /**
     * 浏览器的主要版本（例如，Chrome 为 55）
     */
    private String browserMajorVersion;

    /**
     * 平台名称（例如 Android、iOS、Win7、Win10）
     */
    private String platform;

    /**
     * 平台版本（例如 4.2、10，取决于平台是什么）
     */
    private String platformVersion;

    /**
     * 设备类型（例如手机、台式机、平板电脑、控制台、电视设备）
     */
    private String deviceType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间，不为空，数据库自动生成
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 更新时间，未更新时为空
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateDate;

    /**
     * 逻辑删除，0 未删除，1 删除，MySQL 默认值 0，不为 NULL，注解@TableLogic。
     */
    @TableLogic
    private Boolean deleted;


}
