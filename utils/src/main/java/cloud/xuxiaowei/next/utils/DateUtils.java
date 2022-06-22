package cloud.xuxiaowei.next.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class DateUtils {

	/**
	 * 默认时间格式
	 */
	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

	/**
	 * 默认日期格式
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 默认日期时间格式
	 */
	public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 根据 日期时间、格式 处理为字符串
	 * @param localDateTime 日期时间
	 * @param pattern 格式
	 * @return 返回 字符串
	 */
	public static String format(LocalDateTime localDateTime, String pattern) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		return localDateTime.format(dateTimeFormatter);
	}

	/**
	 * 根据 日期时间、格式 处理为字符串
	 * @param date 日期
	 * @param pattern 格式
	 * @return 返回 字符串
	 */
	public static String format(Date date, String pattern) {
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime expirationDateTime = instant.atZone(zoneId).toLocalDateTime();
		return format(expirationDateTime, pattern);
	}

	/**
	 * 时间戳转日期时间
	 * @param currentTimeMillis 时间戳
	 */
	public static LocalDateTime parse(long currentTimeMillis) {
		return LocalDateTime.ofEpochSecond(currentTimeMillis / 1000, 0, ZoneOffset.of("+8"));
	}

	/**
	 * 时间戳转日期时间
	 * @param currentTimeMillis 时间戳
	 * @param pattern 格式
	 */
	public static String format(long currentTimeMillis, String pattern) {
		LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(currentTimeMillis / 1000, 0, ZoneOffset.of("+8"));
		return format(localDateTime, pattern);
	}

}
