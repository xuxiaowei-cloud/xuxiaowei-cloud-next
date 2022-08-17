package cloud.xuxiaowei.next.utils.http;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * 请求流支持多次获取 {@link HttpServletRequest}
 *
 * @author xuxiaowei
 * @see <a href=
 * "https://stackoverflow.com/questions/10210645/http-servlet-request-lose-params-from-post-body-after-read-it-once/17129256#17129256">http-servlet-request-lose-params-from-post-body-after-read-it-once</a>
 * @since 0.0.1
 */
public class InputStreamHttpServletRequestWrapper extends HttpServletRequestWrapper {

	/**
	 * 用于缓存输入流
	 */
	private ByteArrayOutputStream cachedBytes;

	public InputStreamHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (cachedBytes == null) {
			// 首次获取流时，将流放入 缓存输入流 中
			cacheInputStream();
		}

		// 从 缓存输入流 中获取流并返回
		return new CachedServletInputStream(cachedBytes.toByteArray());
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	/**
	 * 首次获取流时，将流放入 缓存输入流 中
	 */
	private void cacheInputStream() throws IOException {
		// 缓存输入流以便多次读取。为了方便, 我使用 org.apache.commons IOUtils
		cachedBytes = new ByteArrayOutputStream();
		IOUtils.copy(super.getInputStream(), cachedBytes);
	}

	/**
	 * 读取缓存的请求正文的输入流
	 * <p>
	 * 用于根据 缓存输入流 创建一个可返回的 {@link ServletInputStream}
	 */
	public static class CachedServletInputStream extends ServletInputStream {

		private final ByteArrayInputStream input;

		public CachedServletInputStream(byte[] buf) {
			// 从缓存的请求正文创建一个新的输入流
			input = new ByteArrayInputStream(buf);
		}

		@Override
		public boolean isFinished() {
			return false;
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setReadListener(ReadListener listener) {

		}

		@Override
		public int read() throws IOException {
			return input.read();
		}

	}

}
