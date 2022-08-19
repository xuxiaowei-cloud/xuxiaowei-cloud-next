package cloud.xuxiaowei.next.utils;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;

/**
 * dom4j 工具类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class Dom4jUtils {

	/**
	 * Dom4j 转 XML 字符串（不包含 &lt;?xml version="1.0" encoding="UTF-8"?&gt;）
	 * @param document {@link Document}
	 * @return 返回 XML 字符串（不包含 &lt;?xml version="1.0" encoding="UTF-8"?&gt;）
	 */
	public static String asXml(Document document) {
		try {
			StringWriter out = new StringWriter();
			OutputFormat format = new OutputFormat();
			format.setSuppressDeclaration(true);

			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			writer.flush();

			return out.toString();
		}
		catch (IOException e) {
			throw new RuntimeException("生成文本表示时出现 IOException: " + e.getMessage());
		}
	}

}
