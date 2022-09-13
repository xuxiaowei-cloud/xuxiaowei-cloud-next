package cloud.xuxiaowei.next.masterdata.service.impl;

import cloud.xuxiaowei.next.masterdata.bo.CountyHandlePageBo;
import cloud.xuxiaowei.next.masterdata.entity.CountyHandle;
import cloud.xuxiaowei.next.masterdata.mapper.CountyHandleMapper;
import cloud.xuxiaowei.next.masterdata.service.ICountyHandleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 县 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-20
 */
@Service
public class CountyHandleServiceImpl extends ServiceImpl<CountyHandleMapper, CountyHandle>
		implements ICountyHandleService {

	/**
	 * 分页查询县
	 * @param countyHandlePageBo 分页参数
	 * @return 返回 查询结果
	 */
	@Override
	public IPage<CountyHandle> pageByCountyHandlePageBo(CountyHandlePageBo countyHandlePageBo) {
		QueryWrapper<CountyHandle> queryWrapper = new QueryWrapper<>();
		Long current = countyHandlePageBo.getCurrent();
		Long size = countyHandlePageBo.getSize();
		Integer cityCode = countyHandlePageBo.getCityCode();
		Integer countyCode = countyHandlePageBo.getCountyCode();
		String countyName = countyHandlePageBo.getCountyName();

		if (cityCode != null) {
			queryWrapper.eq("city_code", cityCode);
		}
		if (countyCode != null) {
			queryWrapper.eq("county_code", countyCode);
		}
		if (StringUtils.hasText(countyName)) {
			queryWrapper.eq("county_name", countyName);
		}

		IPage<CountyHandle> page = new Page<>(current == null ? 1 : current, size == null ? 10 : size);

		return page(page, queryWrapper);
	}

}
