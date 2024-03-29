package cloud.xuxiaowei.next.utils;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 处理依赖分析
 * <p>
 * 1. mvn clean install
 * <p>
 * 2. <code>mvn dependency:tree > D:/tree.txt</code>
 * <p>
 * 3. 运行 {@link #main()}
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
class DependenciesTests {

	@Test
	void main() {
		File file = new File("D:/tree.txt");
		StringBuilder stringBuilder = new StringBuilder();
		try (// 创建一个新FileReader，给定从中读取文件 。
				FileReader fileReader = new FileReader(file);

				// 创建一个使用默认大小输入缓冲器的缓冲字符输入流。
				BufferedReader bufferedReader = new BufferedReader(fileReader)) {

			String readLine;
			// 读取文本行。 的线被认为是由一个进料线中的任何一个被终止（“\n”），回车（“\r”），或回车立即由换行遵循。
			while ((readLine = bufferedReader.readLine()) != null) {

				if (readLine.contains("[INFO] cloud.xuxiaowei")) {

				}
				else if (readLine.contains("[INFO] +- ")) {

				}
				else if ("[INFO] ".equals(readLine)) {

				}
				else {
					continue;
				}

				readLine = readLine.replace("[INFO] ", "").replace("+- ", "│        └──");
				stringBuilder.append(System.lineSeparator()).append(readLine);

			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(stringBuilder);
	}

}
