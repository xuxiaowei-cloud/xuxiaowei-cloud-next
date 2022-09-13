package cloud.xuxiaowei.masterdata.service;

import cloud.xuxiaowei.next.masterdata.entity.ProvinceHandle;
import cloud.xuxiaowei.next.masterdata.service.IProvinceHandleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 省份 服务类 测试类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@SpringBootTest
class IProvinceHandleServiceTests {

	@Autowired
	private IProvinceHandleService provinceHandleService;

	@Test
	void list() {
		List<ProvinceHandle> provinceHandleList = provinceHandleService.list();
		for (ProvinceHandle provinceHandle : provinceHandleList) {
			log.info(String.valueOf(provinceHandle));
		}
	}

}
