package cloud.xuxiaowei.next.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
	 * @param urlPath URL
	 * @param filePath 路径
	 * @return 文件大小
	 */
	public static int downloadFile(String urlPath, String filePath) {
		HttpURLConnection conn = null;
		InputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			URL url = new URL(urlPath);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			conn.connect();
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = conn.getInputStream();
				outputStream = new FileOutputStream(filePath);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, length);
				}
				log.info("URL：{} 文件：{} 大小：{}", urlPath, filePath, conn.getContentLength());
				return conn.getContentLength();
			}
			else {
				log.error("下载 URL：{} 失败，状态码：{}", urlPath, conn.getResponseCode());
				return 0;
			}
		}
		catch (MalformedURLException e) {
			log.error("创建下载 URL：{} 异常：", urlPath, e);
			throw new RuntimeException(e);
		}
		catch (IOException e) {
			log.error("打开 URL：{} 链接异常：", urlPath, e);
			throw new RuntimeException(e);
		}
		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				}
				catch (IOException e) {
					log.error("关闭 inputStream 异常：", e);
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				}
				catch (IOException e) {
					log.error("关闭 outputStream 异常：", e);
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
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
					if (!fileObject.exists()) {
						boolean mkdir = fileObject.mkdirs();
						if (!mkdir) {
							log.error("创建解压目录 {} 失败！", fileObject.getAbsolutePath());
							throw new RuntimeException("创建解压目录失败！");
						}
					}
				}
			}

			int bufferSize = 1024;

			Enumeration<? extends ZipEntry> e = zipfile.entries();
			while (e.hasMoreElements()) {
				entry = e.nextElement();
				if (entry.isDirectory()) {
					continue;
				}
				String entryName = entry.getName();
				if ("".equals(entryName.trim())) {
					log.warn("entryName is null or empty, entry={}", entry);
					continue;
				}
				try (InputStream inputStream = zipfile.getInputStream(entry);
						OutputStream outputStream = new FileOutputStream(zipDir + File.separator + entryName);
						BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
						BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream,
								bufferSize)) {

					int count;
					byte[] dataByte = new byte[bufferSize];

					while ((count = bufferedInputStream.read(dataByte, 0, bufferSize)) != -1) {
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
		catch (IOException e) {
			log.error("创建压缩文件异常：{}", filePath, e);
			throw new RuntimeException(e);
		}

	}

}
