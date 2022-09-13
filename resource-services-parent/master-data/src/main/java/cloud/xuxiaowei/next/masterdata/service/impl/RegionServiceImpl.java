package cloud.xuxiaowei.next.masterdata.service.impl;

import cloud.xuxiaowei.next.masterdata.bo.RegionPageBo;
import cloud.xuxiaowei.next.masterdata.mapper.RegionMapper;
import cloud.xuxiaowei.next.masterdata.service.RegionService;
import cloud.xuxiaowei.next.masterdata.vo.RegionPageVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 区域 服务实现类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Service
public class RegionServiceImpl implements RegionService {

	@Resource
	private RegionMapper regionMapper;

	/**
	 * 分页查询区域
	 * @param regionPageBo 分页参数
	 * @return 返回 查询结果
	 */
	@Override
	public IPage<RegionPageVo> page(RegionPageBo regionPageBo) {
		Long current = regionPageBo.getCurrent();
		Long size = regionPageBo.getSize();
		regionPageBo.setCurrent(current == null ? 1 : current);
		regionPageBo.setSize(size == null ? 10 : size);

		long total = regionMapper.pageCount(regionPageBo);
		List<RegionPageVo> records = regionMapper.page(regionPageBo);

		Page<RegionPageVo> page = new Page<>(regionPageBo.getCurrent(), regionPageBo.getSize(), total);
		page.setRecords(records);
		return page;
	}

}
