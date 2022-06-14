package cloud.xuxiaowei.next.utils;

/**
 * 客户端类型枚举
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public enum ClientType {

    /**
     * 微信客户端
     */
    WECHAT_APPLET("wechat_applet", "password");

    /**
     * 客户端类型
     */
    public final String clientType;

    /**
     * 授权类型
     */
    public final String grantType;

    ClientType(String clientType, String grantType) {
        this.clientType = clientType;
        this.grantType = grantType;
    }

}
