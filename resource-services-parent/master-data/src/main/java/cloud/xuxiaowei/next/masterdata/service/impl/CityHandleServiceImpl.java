package cloud.xuxiaowei.next.masterdata.service.impl;

import cloud.xuxiaowei.next.masterdata.bo.CityHandlePageBo;
import cloud.xuxiaowei.next.masterdata.entity.CityHandle;
import cloud.xuxiaowei.next.masterdata.mapper.CityHandleMapper;
import cloud.xuxiaowei.next.masterdata.service.ICityHandleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 城市 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-20
 */
@Service
public class CityHandleServiceImpl extends ServiceImpl<CityHandleMapper, CityHandle> implements ICityHandleService {

	/**
	 * 分页查询城市
	 * @param cityHandlePageBo 分页参数
	 * @return 返回 查询结果
	 */
	@Override
	public IPage<CityHandle> pageByCityHandlePageBo(CityHandlePageBo cityHandlePageBo) {
		QueryWrapper<CityHandle> queryWrapper = new QueryWrapper<>();
		Long current = cityHandlePageBo.getCurrent();
		Long size = cityHandlePageBo.getSize();
		Integer provinceCode = cityHandlePageBo.getProvinceCode();
		Integer cityCode = cityHandlePageBo.getCityCode();
		String cityName = cityHandlePageBo.getCityName();

		if (provinceCode != null) {
			queryWrapper.eq("province_code", provinceCode);
		}
		if (cityCode != null) {
			queryWrapper.eq("city_code", cityCode);
		}
		if (StringUtils.hasText(cityName)) {
			queryWrapper.eq("city_name", cityName);
		}

		IPage<CityHandle> page = new Page<>(current == null ? 1 : current, size == null ? 10 : size);

		return page(page, queryWrapper);
	}

}
