package cloud.xuxiaowei.next.canal.service.impl;

import cloud.xuxiaowei.next.canal.mapper.CanalMapper;
import cloud.xuxiaowei.next.canal.service.CanalService;
import com.baomidou.dynamic.datasource.annotation.DS;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * canal 服务 接口实现类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Service
@DS("log")
public class CanalServiceImpl implements CanalService {

	@Resource
	private CanalMapper canalMapper;

	@Override
	public int insert(String sql) {
		return canalMapper.insert(sql);
	}

	@Override
	public int update(String sql) {
		return canalMapper.update(sql);
	}

	@Override
	public int delete(String sql) {
		return canalMapper.delete(sql);
	}

}
