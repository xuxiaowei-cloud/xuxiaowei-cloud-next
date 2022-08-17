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
	public static final List<String> LOWER_CASE_LIST = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
			"k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");

	/**
	 * 大写字母
	 */
	public static final List<String> UPPER_CASE_LIST = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");

	/**
	 * 符号
	 */
	public static final List<String> SYMBOL = Arrays.asList("!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_",
			"=", "+", "[", "{", "]", "}", ";", ":", "'", "\"", ",", "<", ".", ">", "/", "?");

	/**
	 * 时间戳字段
	 */
	public static final String CURRENT_TIME_MILLIS = "currentTimeMillis";

	/**
	 * HTTP 响应中加密描述的字段
	 */
	public static final String ENCRYPT = "encrypt";

	/**
	 * HTTP 响应中解密描述的字段
	 */
	public static final String DECRYPT = "decrypt";

	/**
	 * HTTP 响应中签名描述的字段
	 */
	public static final String SIGN = "sign";

	/**
	 * 默认值
	 */
	public static final String DEFAULT = "default";

	/**
	 * Token 中传递权限的名称
	 */
	public static final String AUTHORITIES = "authorities";

	/**
	 * 端点
	 */
	public static final String ACTUATOR = "actuator";

	/**
	 * 用户名
	 */
	public static final String USERNAME = "username";

	/**
	 * 用户ID
	 */
	public static final String USERS_ID = "USERS_ID";

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
	 * 授权类型
	 */
	public static final String GRANT_TYPE = "grant_type";

	/**
	 * 私钥
	 */
	public static final String PRIVATE_KEY = "privateKey";

	/**
	 * 日志主键
	 */
	public static final String LOG_ID = "logId";

}
