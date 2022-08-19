package cloud.xuxiaowei.next.utils;

import java.net.URI;

/**
 * URL 工具类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class UrlUtils {

	/**
	 * 根据 URI、参数名，获取参数值
	 * @param uri URI
	 * @param queryName 参数名
	 * @return 返回 参数值
	 */
	public static String getQueryValue(URI uri, String queryName) {
		if (uri == null || queryName == null) {
			return null;
		}
		String uriQuery = uri.getQuery();
		String[] querySplit = uriQuery.split("&");
		for (String query : querySplit) {
			String[] split = query.split("=");
			if (queryName.equals(split[0])) {
				return split[1];
			}
		}
		return null;
	}

}
