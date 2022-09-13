package cloud.xuxiaowei.next.masterdata.service.impl;

import cloud.xuxiaowei.next.masterdata.bo.TownHandlePageBo;
import cloud.xuxiaowei.next.masterdata.entity.TownHandle;
import cloud.xuxiaowei.next.masterdata.mapper.TownHandleMapper;
import cloud.xuxiaowei.next.masterdata.service.ITownHandleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 镇 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-20
 */
@Service
public class TownHandleServiceImpl extends ServiceImpl<TownHandleMapper, TownHandle> implements ITownHandleService {

	/**
	 * 分页查询镇
	 * @param townHandlePageBo 分页参数
	 * @return 返回 查询结果
	 */
	@Override
	public IPage<TownHandle> pageByTownHandlePageBo(TownHandlePageBo townHandlePageBo) {
		QueryWrapper<TownHandle> queryWrapper = new QueryWrapper<>();
		Long current = townHandlePageBo.getCurrent();
		Long size = townHandlePageBo.getSize();
		Integer countyCode = townHandlePageBo.getCountyCode();
		Integer townCode = townHandlePageBo.getTownCode();
		String townName = townHandlePageBo.getTownName();

		if (countyCode != null) {
			queryWrapper.eq("county_code", countyCode);
		}
		if (townCode != null) {
			queryWrapper.eq("town_code", townCode);
		}
		if (StringUtils.hasText(townName)) {
			queryWrapper.eq("town_name", townName);
		}

		IPage<TownHandle> page = new Page<>(current == null ? 1 : current, size == null ? 10 : size);

		return page(page, queryWrapper);
	}

}
