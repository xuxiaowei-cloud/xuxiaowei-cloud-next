package cloud.xuxiaowei.next.utils;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

/**
 * 反射 工具类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class ReflectionsUtils {

	/**
	 * 扫描指定包下的类型注解
	 * @param packages 扫描的包
	 * @return 返回 反射扫描结果
	 */
	public static Reflections typesAnnotated(String... packages) {
		return new Reflections(new ConfigurationBuilder().forPackages(packages).addScanners(Scanners.TypesAnnotated));
	}

}
