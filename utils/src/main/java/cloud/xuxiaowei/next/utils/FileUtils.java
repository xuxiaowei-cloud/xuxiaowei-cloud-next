package cloud.xuxiaowei.next.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 文件工具类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
public class FileUtils {

	/**
	 * 根据 URL 路径 下载文件
	 * @param uri URL
	 * @param filePath 路径
	 * @return 文件大小
	 */
	public static int downloadFile(String uri, String filePath) {

		URL url;
		try {
			url = new URL(uri);
		}
		catch (MalformedURLException e) {
			log.error("创建下载 URL：{} 异常：", uri, e);
			throw new RuntimeException(e);
		}

		URLConnection urlConnection;
		try {
			urlConnection = url.openConnection();
		}
		catch (IOException e) {
			log.error("打开 URL：{} 链接异常：", uri, e);
			throw new RuntimeException(e);
		}

		InputStream inputStream;
		try {
			inputStream = urlConnection.getInputStream();
		}
		catch (IOException e) {
			log.error("获取 URL：{} 输入流异常：", uri, e);
			throw new RuntimeException(e);
		}

		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(filePath);
		}
		catch (FileNotFoundException e) {
			log.error("URL：{} 文件：{} 输出流异常：", uri, filePath, e);
			throw new RuntimeException(e);
		}

		byte[] buffer = new byte[1204];
		int byteSum = 0;
		int byteRead;
		while (true) {
			try {
				if ((byteRead = inputStream.read(buffer)) == -1) {
					break;
				}
			}
			catch (IOException e) {
				log.error("URL：{} 文件：{} 读取异常：", uri, filePath, e);
				throw new RuntimeException(e);
			}
			byteSum += byteRead;
			try {
				fileOutputStream.write(buffer, 0, byteRead);
			}
			catch (IOException e) {
				log.error("URL：{} 文件：{} 写入异常：", uri, filePath, e);
				throw new RuntimeException(e);
			}
		}

		log.info("URL：{} 文件：{} 大小：{}", uri, filePath, byteSum);

		return byteSum;
	}

	/**
	 * 解压 ZIP
	 * @param filePath 压缩文件夹路径
	 * @param zipDir 解压后的路径
	 */
	public static void unzip(String filePath, String zipDir) {

		try (ZipFile zipfile = new ZipFile(filePath)) {
			Enumeration<? extends ZipEntry> dir = zipfile.entries();

			String name;
			ZipEntry entry;

			while (dir.hasMoreElements()) {
				entry = dir.nextElement();

				if (entry.isDirectory()) {
					name = entry.getName();
					name = name.substring(0, name.length() - 1);
					File fileObject = new File(zipDir + File.separator + name);
					boolean mkdir = fileObject.mkdir();
				}
			}

			int buffer = 1024;

			Enumeration<? extends ZipEntry> e = zipfile.entries();
			while (e.hasMoreElements()) {
				entry = e.nextElement();
				if (entry.isDirectory()) {

				}
				else {
					try (BufferedInputStream bufferedInputStream = new BufferedInputStream(
							zipfile.getInputStream(entry));
							FileOutputStream fileOutputStream = new FileOutputStream(
									zipDir + File.separator + entry.getName());
							BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream,
									buffer)) {

						int count;
						byte[] dataByte = new byte[buffer];

						while ((count = bufferedInputStream.read(dataByte, 0, buffer)) != -1) {
							bufferedOutputStream.write(dataByte, 0, count);
						}

					}
					catch (FileNotFoundException ex) {
						log.error("文件不存在异常：{}", filePath, ex);
						throw new RuntimeException(ex);
					}
					catch (IOException ex) {
						log.error("解压异常：{}", filePath, ex);
						throw new RuntimeException(ex);
					}
				}
			}

		}
		catch (IOException e) {
			log.error("创建压缩文件异常：{}", filePath, e);
			throw new RuntimeException(e);
		}

	}

}
