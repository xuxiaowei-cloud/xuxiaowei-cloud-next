package cloud.xuxiaowei.next.masterdata.service.impl;

import cloud.xuxiaowei.next.masterdata.bo.VillageHandlePageBo;
import cloud.xuxiaowei.next.masterdata.entity.VillageHandle;
import cloud.xuxiaowei.next.masterdata.mapper.VillageHandleMapper;
import cloud.xuxiaowei.next.masterdata.service.IVillageHandleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 居委会 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-20
 */
@Service
public class VillageHandleServiceImpl extends ServiceImpl<VillageHandleMapper, VillageHandle>
		implements IVillageHandleService {

	/**
	 * 分页查询居委会
	 * @param villageHandlePageBo 分页参数
	 * @return 返回 查询结果
	 */
	@Override
	public IPage<VillageHandle> pageByVillageHandlePageBo(VillageHandlePageBo villageHandlePageBo) {
		QueryWrapper<VillageHandle> queryWrapper = new QueryWrapper<>();
		Long current = villageHandlePageBo.getCurrent();
		Long size = villageHandlePageBo.getSize();
		Integer townCode = villageHandlePageBo.getTownCode();
		Long villageCode = villageHandlePageBo.getVillageCode();
		String villageName = villageHandlePageBo.getVillageName();
		String villageTypeCode = villageHandlePageBo.getVillageTypeCode();

		if (townCode != null) {
			queryWrapper.eq("town_code", townCode);
		}
		if (villageCode != null) {
			queryWrapper.eq("village_code", villageCode);
		}
		if (StringUtils.hasText(villageName)) {
			queryWrapper.eq("village_name", villageName);
		}
		if (StringUtils.hasText(villageTypeCode)) {
			queryWrapper.eq("village_type_code", villageTypeCode);
		}

		IPage<VillageHandle> page = new Page<>(current == null ? 1 : current, size == null ? 10 : size);

		return page(page, queryWrapper);
	}

}
