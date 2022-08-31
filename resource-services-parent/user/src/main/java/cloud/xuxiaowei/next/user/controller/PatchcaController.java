package cloud.xuxiaowei.next.user.controller;

import cloud.xuxiaowei.next.core.properties.CloudPatchcaProperties;
import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.exception.CloudRuntimeException;
import com.github.bingoohuang.patchca.custom.ConfigurableCaptchaService;
import com.github.bingoohuang.patchca.filter.predefined.*;
import com.github.bingoohuang.patchca.font.RandomFontFactory;
import com.github.bingoohuang.patchca.utils.encoder.EncoderHelper;
import com.github.bingoohuang.patchca.word.RandomWordFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;
import java.io.IOException;
import java.time.Duration;
import java.util.Random;

/**
 * 图片验证码
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Controller
@RequestMapping("/patchca")
public class PatchcaController {

	/**
	 * 全自动区分计算机和人类的图灵测试服务
	 */
	private static final ConfigurableCaptchaService CS = new ConfigurableCaptchaService();

	/**
	 * 随机数
	 */
	private static final Random RANDOM = new Random();

	private CloudPatchcaProperties cloudPatchcaProperties;

	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	public void setCloudPatchcaProperties(CloudPatchcaProperties cloudPatchcaProperties) {
		this.cloudPatchcaProperties = cloudPatchcaProperties;
	}

	@Autowired
	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	/**
	 * 图片验证码
	 * @param request 请求
	 * @param response 响应
	 */
	@RequestMapping()
	public void index(HttpServletRequest request, HttpServletResponse response) {
		try {
			responsePatchca(request, response);
		}
		catch (IOException e) {
			log.error("生成图片验证码异常：", e);
			throw new CloudRuntimeException("生成图片验证码异常");
		}
	}

	/**
	 * 获取全自动区分计算机和人类的图灵测试对应的字符串
	 */
	private void responsePatchca(HttpServletRequest request, HttpServletResponse response) throws IOException {

		init();

		// @formatter:off
		switch (RANDOM.nextInt(5)) {
			case 0:
				// 摆动波纹
				CS.setFilterFactory(new WobbleRippleFilterFactory());
				break;
			case 1:
				// 大理石纹波
				CS.setFilterFactory(new MarbleRippleFilterFactory());
				break;
			case 2:
				// 双纹波
				CS.setFilterFactory(new DoubleRippleFilterFactory());
				break;
			case 3:
				// 曲线波纹
				CS.setFilterFactory(new CurvesRippleFilterFactory(CS.getColorFactory()));
				break;
			case 4:
				// 漫反射纹波
				CS.setFilterFactory(new DiffuseRippleFilterFactory());
				break;
			default:
		}
		// @formatter:on

		// 设置全自动区分计算机和人类的图灵测试的响应
		setPngHeaders(response);

		HttpSession session = request.getSession();
		String sessionId = session.getId();

		// 图片验证码的字符串
		// 响应图片
		String challenge = EncoderHelper.getChallangeAndWriteImage(CS, "png", response.getOutputStream());
		log.info("图片验证码：{}", challenge);

		// 储存图片验证码
		redisStore(sessionId, challenge);
	}

	private void init() {
		/* cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170))); */

		// 设置颜色
		CS.setColorFactory(index -> {

			// 创建一个数组，用于储存 rgb 颜色
			int[] c = new int[3];

			// 在 3 以内的随机数
			int i = RANDOM.nextInt(c.length);

			// 使用两次随机数组合（第一次在0、1、2，第二次在0-71或0-256），
			// 使得rgb颜色的三个数，必有两个分别在0-71和0-256之间，另一个必在0-71或0-256中
			for (int fi = 0; fi < c.length; fi++) {
				if (fi == i) {
					// 在 71 以内的随机数
					c[fi] = RANDOM.nextInt(71);
				}
				else {
					// 在 256 以内的随机数
					c[fi] = RANDOM.nextInt(256);
				}
			}

			// 返回颜色
			return new Color(c[0], c[1], c[2]);
		});

		// 随机字符
		RandomWordFactory randomWordFactory = new RandomWordFactory();

		// 字符范围
		// 去掉了容易混淆的 1、0、l、o、I、O
		// 23456789abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ
		randomWordFactory.setCharacters(cloudPatchcaProperties.getCharacters());

		// 字符数量范围
		randomWordFactory.setMaxLength(cloudPatchcaProperties.getMaxLength());
		randomWordFactory.setMinLength(cloudPatchcaProperties.getMinLength());

		// 设置随机字符
		CS.setWordFactory(randomWordFactory);

		// 图片像素
		CS.setHeight(cloudPatchcaProperties.getHeight());
		CS.setWidth(cloudPatchcaProperties.getWidth());

		// 字体大小范围
		RandomFontFactory randomFontFactory = new RandomFontFactory();

		// 字体最大大小
		randomFontFactory.setMaxSize(cloudPatchcaProperties.getMaxSize());
		// 字体最小大小
		randomFontFactory.setMinSize(cloudPatchcaProperties.getMinSize());

		// 设置 字体大小范围
		CS.setFontFactory(randomFontFactory);
	}

	/**
	 * 储存
	 * @param sessionId Session ID
	 * @param challenge 图片验证码的文字内容
	 */
	private void redisStore(String sessionId, String challenge) {

		String key = Constant.PATCHCA + ":" + sessionId;

		// 将图片验证码放入Redis中
		stringRedisTemplate.opsForValue().set(key, challenge, Duration.ofMinutes(cloudPatchcaProperties.getMinutes()));
	}

	/**
	 * 设置全自动区分计算机和人类的图灵测试的响应
	 * <p>
	 * 禁止全自动区分计算机和人类的图灵测试缓存
	 * @param response 响应
	 */
	public static void setPngHeaders(HttpServletResponse response) {
		// 发送到客户端的响应的内容类型
		response.setContentType(MediaType.IMAGE_PNG_VALUE);

		// 设置具有给定名称和值的响应标头。
		// 如果已设置标头，则新值将覆盖前一个标头。
		// Cache-control：指定所有缓存机制在整个请求/响应链中必须服从的指令。
		// 这些指令指定用于阻止缓存对请求或响应造成不利干扰的行为。
		// 这些指令通常覆盖默认缓存算法。
		// 缓存指令是单向的，即请求中存在一个指令并不意味着响应中将存在同一个指令。
		// no-cache：必须先与服务器确认返回的响应是否被更改，然后才能使用该响应来满足后续对同一个网址的请求。
		// 因此，如果存在合适的验证令牌 (ETag)，no-cache 会发起往返通信来验证缓存的响应，如果资源未被更改，可以避免下载。
		// no-store：所有内容都不会被缓存到缓存或 Internet 临时文件中
		response.setHeader(HttpHeaders.CACHE_CONTROL, CacheControl.noCache().getHeaderValue());
		response.setHeader(HttpHeaders.PRAGMA, CacheControl.noCache().getHeaderValue());

		// 时间戳
		long time = System.currentTimeMillis();

		// 设置具有给定名称和日期值的响应标头。
		// 日期以自纪元以来的毫秒数指定。
		// 如果已设置标头，则新值将覆盖前一个标头。
		// Last-Modified：在浏览器第一次请求某一个URL时，服务器端的返回状态会是200，内容是客户端请求的资源，
		// 同时有一个Last-Modified的属性标记此文件在服务器端最后被修改的时间。
		response.setHeader(HttpHeaders.LAST_MODIFIED, time + "");
		response.setHeader(HttpHeaders.DATE, time + "");
		// Expires：Expires是RFC 2616（HTTP/1.0）协议中和网页缓存相关字段。
		// 用来控制缓存的失效日期，要注意的是，HTTP/1.0有一个功能比较弱的缓存控制机制：Pragma，使用HTTP/1.0的缓存将忽略Expires和Cache-Control头。
		response.setHeader(HttpHeaders.EXPIRES, time + "");
	}

}
