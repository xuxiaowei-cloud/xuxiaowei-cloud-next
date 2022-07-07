package cloud.xuxiaowei.next.utils;

/**
 * 服务 枚举
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public enum ServiceEnums {

	/**
	 * 网关服务
	 */
	GATEWAY("gateway", "网关服务"),

	/**
	 * 监控（管理）服务
	 */
	ADMIN_SERVER("admin-server", "监控（管理）服务"),

	/**
	 * 登录服务
	 */
	PASSPORT("passport", "登录服务"),

	/**
	 * 审计服务
	 */
	AUDIT("audit", "审计服务"),

	/**
	 * 阿里巴巴 MySQL binlog 增量订阅、消费服务
	 */
	CANAL("canal", "阿里巴巴 MySQL binlog 增量订阅、消费服务"),

	/**
	 * 用户服务
	 */
	USER("user", "用户服务"),

	/**
	 * WebSocket 服务
	 */
	WEBSOCKET("websocket", "WebSocket 服务"),

	/**
	 * WebService 服务
	 */
	WEBSERVICE("webservice", "WebService 服务"),

	/**
	 * 微信小程序服务
	 */
	WECHAT_MINIPROGRAM("wechat-miniprogram", "微信小程序服务"),

	/**
	 * XXL Job Admin
	 */
	XXL_JOB_ADMIN("xxl-job-admin", "XXL Job Admin"),

	;

	public final String service;

	public final String name;

	ServiceEnums(String service, String serviceName) {
		this.service = service;
		this.name = serviceName;
	}

	public static ServiceEnums getEnum(String value) {
		ServiceEnums[] enums = ServiceEnums.values();
		for (ServiceEnums e : enums) {
			if (e.service.equals(value)) {
				return e;
			}
		}
		return null;
	}

}
