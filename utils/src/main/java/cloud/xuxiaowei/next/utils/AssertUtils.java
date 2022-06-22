package cloud.xuxiaowei.next.utils;

import cloud.xuxiaowei.next.utils.exception.CloudRuntimeException;

import java.util.List;

/**
 * 断言
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class AssertUtils {

	/**
	 * 最小长度断言
	 * @param list 数据
	 * @param min 最小长度
	 * @param message 消息
	 */
	public static void min(List<?> list, int min, String message) {
		if (list != null) {
			if (list.size() < min) {
				throw new CloudRuntimeException(message);
			}
		}
	}

	/**
	 * 最小长度断言
	 * @param list 数据
	 * @param min 最小长度
	 * @param message 消息
	 */
	public static void minNonNull(List<?> list, int min, String message) {
		if (list == null) {
			throw new CloudRuntimeException(message);
		}

		if (list.size() < min) {
			throw new CloudRuntimeException(message);
		}
	}

	/**
	 * 最小长度断言
	 * @param string 数据
	 * @param min 最小长度
	 * @param message 消息
	 */
	public static void min(String string, int min, String message) {
		if (string != null) {
			if (string.length() < min) {
				throw new CloudRuntimeException(message);
			}
		}
	}

	/**
	 * 最小长度断言
	 * @param string 数据
	 * @param min 最小长度
	 * @param message 消息
	 */
	public static void minNonNull(String string, int min, String message) {
		if (string == null) {
			throw new CloudRuntimeException(message);
		}

		if (string.length() < min) {
			throw new CloudRuntimeException(message);
		}
	}

	/**
	 * 最大长度断言
	 * @param list 数据
	 * @param max 最大长度
	 * @param message 消息
	 */
	public static void max(List<?> list, int max, String message) {
		if (list != null) {
			if (list.size() > max) {
				throw new CloudRuntimeException(message);
			}
		}
	}

	/**
	 * 最大长度断言
	 * @param list 数据
	 * @param max 最大长度
	 * @param message 消息
	 */
	public static void maxNonNull(List<?> list, int max, String message) {
		if (list == null) {
			throw new CloudRuntimeException(message);
		}

		if (list.size() > max) {
			throw new CloudRuntimeException(message);
		}
	}

	/**
	 * 最大长度断言
	 * @param string 数据
	 * @param max 最大长度
	 * @param message 消息
	 */
	public static void max(String string, int max, String message) {
		if (string != null) {
			if (string.length() > max) {
				throw new CloudRuntimeException(message);
			}
		}
	}

	/**
	 * 最大长度断言
	 * @param string 数据
	 * @param max 最大长度
	 * @param message 消息
	 */
	public static void maxNonNull(String string, int max, String message) {
		if (string == null) {
			throw new CloudRuntimeException(message);
		}

		if (string.length() > max) {
			throw new CloudRuntimeException(message);
		}
	}

	/**
	 * 长度断言
	 * @param list 数据
	 * @param min 最小长度
	 * @param max 最大长度
	 * @param message 消息
	 */
	public static void size(List<?> list, int min, int max, String message) {
		if (list != null) {
			if (list.size() < min) {
				throw new CloudRuntimeException(message);
			}
			if (list.size() > max) {
				throw new CloudRuntimeException(message);
			}
		}
	}

	/**
	 * 长度断言
	 * @param list 数据
	 * @param min 最小长度
	 * @param max 最大长度
	 * @param message 消息
	 */
	public static void sizeNonNull(List<?> list, int min, int max, String message) {
		if (list == null) {
			throw new CloudRuntimeException(message);
		}
		if (list.size() < min) {
			throw new CloudRuntimeException(message);
		}
		if (list.size() > max) {
			throw new CloudRuntimeException(message);
		}
	}

	/**
	 * 长度断言
	 * @param string 数据
	 * @param min 最小长度
	 * @param max 最大长度
	 * @param message 消息
	 */
	public static void size(String string, int min, int max, String message) {
		if (string != null) {
			if (string.length() < min) {
				throw new CloudRuntimeException(message);
			}
			if (string.length() > max) {
				throw new CloudRuntimeException(message);
			}
		}
	}

	/**
	 * 长度断言
	 * @param string 数据
	 * @param min 最小长度
	 * @param max 最大长度
	 * @param message 消息
	 */
	public static void sizeNonNull(String string, int min, int max, String message) {
		if (string == null) {
			throw new CloudRuntimeException(message);
		}
		if (string.length() < min) {
			throw new CloudRuntimeException(message);
		}
		if (string.length() > max) {
			throw new CloudRuntimeException(message);
		}
	}

	/**
	 * 断言一个对象是null
	 * @param object 要检查的对象
	 * @param message 断言失败时使用的异常消息
	 */
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new CloudRuntimeException(message);
		}
	}

	/**
	 * 断言一个对象不是null
	 * @param object 要检查的对象
	 * @param message 断言失败时使用的异常消息
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new CloudRuntimeException(message);
		}
	}

}
