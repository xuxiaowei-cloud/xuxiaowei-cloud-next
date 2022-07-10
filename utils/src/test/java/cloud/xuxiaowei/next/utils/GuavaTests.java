package cloud.xuxiaowei.next.utils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * Guava 测试类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
class GuavaTests {

	/**
	 * List 转 String
	 */
	@Test
	void listToString() {
		List<String> nameList = Lists.newArrayList("张三", "李四", "王二", "麻子");
		String result = Joiner.on(",").join(nameList);
		log.info(result);
	}

	/**
	 * String 转 List
	 */
	@Test
	void stringToList() {
		String str = "张三; 李四 ; 王二 ;麻子";
		List<String> list = Splitter.on(";").trimResults().splitToList(str);
		log.info(String.valueOf(list));
	}

	/**
	 * Map 转 String
	 */
	@Test
	void mapToString() {
		Map<String, Integer> ageMap = Maps.newHashMap();
		ageMap.put("张三", 15);
		ageMap.put("李四", 60);
		ageMap.put("王二", 23);
		ageMap.put("麻子", 45);
		String result = Joiner.on(" , ").withKeyValueSeparator(" = ").join(ageMap);
		log.info(result);
	}

	/**
	 * String 转 Map
	 */
	@Test
	void stringToMap() {
		String input = "张三=15 ,李四=60, 王二=23 , 麻子=45";
		Map<String, String> result = Splitter.on(",").trimResults().withKeyValueSeparator("=").split(input);
		log.info(String.valueOf(result));
	}

}
