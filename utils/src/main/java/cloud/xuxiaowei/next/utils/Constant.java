package cloud.xuxiaowei.next.utils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 常量
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class Constant implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数字
     */
    public static final List<String> NUMBER_LIST = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

    /**
     * 小写字母
     */
    public static final List<String> LOWER_CASE_LIST = Arrays.asList(
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z");

    /**
     * 大写字母
     */
    public static final List<String> UPPER_CASE_LIST = Arrays.asList(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z");

    /**
     * 符号
     */
    public static final List<String> SYMBOL = Arrays.asList(
            "!", "@", "#", "$", "%", "^", "&", "*", "(", ")",
            "-", "_", "=", "+", "[", "{", "]", "}", ";", ":",
            "'", "\"", ",", "<", ".", ">", "/", "?");

    /**
     * 端点
     */
    public static final String ACTUATOR = "actuator";

    /**
     * 用户名
     */
    public static final String USERNAME = "username";

    /**
     * NAME
     */
    public static final String NAME = "NAME";

    /**
     * 请求ID
     */
    public static final String REQUEST_ID = "REQUEST_ID";

    /**
     * IP
     */
    public static final String IP = "IP";

    /**
     * null 值
     */
    public static final String NULL = "null";

    /**
     * 参数名
     */
    public static final String PARAMETER_NAME = "parameterName";

    /**
     * 参数类型
     */
    public static final String PARAMETER_TYPE = "parameterType";

    /**
     * 范围
     */
    public static final String SCOPE = "scope";

    /**
     * 不明确
     */
    public static final String UNDEFINED = "undefined";

    /**
     * 微信小程序appid
     */
    public static final String APPID = "appid";

    /**
     * 微信小程序 openid
     */
    public static final String OPENID = "openid";

    /**
     * 微信小程序 unionid
     */
    public static final String UNIONID = "unionid";

    /**
     * 客户端类型
     */
    public static final String CLIENT_TYPE = "client_type";

    /**
     * 私钥
     */
    public static final String PRIVATE_KEY = "privateKey";

}
