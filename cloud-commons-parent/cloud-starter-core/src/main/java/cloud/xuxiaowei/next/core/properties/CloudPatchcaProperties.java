package cloud.xuxiaowei.next.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 微服务 图片验证码 配置
 * <p>
 * 运行时刷新
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties("cloud.patchca")
public class CloudPatchcaProperties {

	/**
	 * 图片验证路径
	 */
	private String url = "/patchca";

	/**
	 * 图片验证码中可能出现的字符串
	 * <p>
	 * 去掉了容易混淆的 1、0、l、o、I、O
	 */
	private String characters = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";

	/**
     * 最多字符串个数
	 */
	private int maxLength = 5;

    /**
     * 最少字符串个数
	 */
	private int minLength = 3;

	/**
     * 图片宽度
	 */
	private int width = 150;

    /**
     * 图片高度
	 */
	private int height = 50;

	/**
     * 字体最小值
	 */
	private int minSize = 25;

	/**
	 * 字体最大值
	 */
	private int maxSize = 35;

}
