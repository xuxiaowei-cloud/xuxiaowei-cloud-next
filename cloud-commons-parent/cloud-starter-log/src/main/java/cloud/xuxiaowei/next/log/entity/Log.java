package cloud.xuxiaowei.next.log.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 日志表
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-04-01
 */
@Data
public class Log implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 日志表，主键，自增
	 */
	@TableId(value = "log_id", type = IdType.AUTO)
	private Long logId;

	/**
	 * 模块，不为空
	 */
	private String module;

	/**
	 * 日期，不为空
	 */
	@TableField("`date`")
	private LocalDate date;

	/**
	 * 年，不为空
	 */
	@TableField("`year`")
	private Integer year;

	/**
	 * 月，不为空
	 */
	@TableField("`month`")
	private Integer month;

	/**
	 * 日，不为空
	 */
	@TableField("`day`")
	private Integer day;

	/**
	 * 小时，不为空
	 */
	@TableField("`hour`")
	private Integer hour;

	/**
	 * 方法，不为空
	 */
	private String method;

	/**
	 * URI，不为空
	 */
	private String requestUri;

	/**
	 * 参数
	 */
	private String queryString;

	/**
	 * 请求头，不为空，json
	 */
	private String headersMap;

	/**
	 * headers中authorization
	 */
	private String authorization;

	/**
	 * authorization解密
	 */
	private String payload;

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
	 * 创建时间，不为空
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createDate;

	/**
	 * 更新时间，未更新时为空
	 */
	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateDate;

	/**
	 * 创建人，不为空
	 * <p>
	 * 日志表中的创建者，不使用 MyBatis Plus 自动填充，而是在保存时进行设置
	 */
	private String createUsersId;

	/**
	 * 更新人，未更新时为空
	 */
	@TableField(fill = FieldFill.UPDATE)
	private String updateUsersId;

	/**
	 * 创建者IP，不为空
	 */
	@TableField(fill = FieldFill.INSERT)
	private String createIp;

	/**
	 * 更新者IP，未更新时为空
	 */
	@TableField(fill = FieldFill.UPDATE)
	private String updateIp;

	/**
	 * 主机名
	 */
	private String hostname;

	/**
	 * 主机IP
	 */
	private String ipAddress;

	/**
	 * 服务端口
	 */
	@TableField("`port`")
	private Integer port;

	/**
	 * 逻辑删除，0 未删除，1 删除，MySQL 默认值 0，不为 NULL，注解@TableLogic。
	 */
	@TableLogic
	private Boolean deleted;

}
