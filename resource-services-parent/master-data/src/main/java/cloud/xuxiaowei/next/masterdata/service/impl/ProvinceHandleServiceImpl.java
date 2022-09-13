package cloud.xuxiaowei.next.masterdata.service.impl;

import cloud.xuxiaowei.next.masterdata.bo.ProvinceHandlePageBo;
import cloud.xuxiaowei.next.masterdata.entity.ProvinceHandle;
import cloud.xuxiaowei.next.masterdata.mapper.ProvinceHandleMapper;
import cloud.xuxiaowei.next.masterdata.service.IProvinceHandleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 省份 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-20
 */
@Service
public class ProvinceHandleServiceImpl extends ServiceImpl<ProvinceHandleMapper, ProvinceHandle>
		implements IProvinceHandleService {

	/**
	 * 分页查询省份
	 * @param provinceHandlePageBo 分页参数
	 * @return 返回 查询结果
	 */
	@Override
	public IPage<ProvinceHandle> pageByProvinceHandlePageBo(ProvinceHandlePageBo provinceHandlePageBo) {
		QueryWrapper<ProvinceHandle> queryWrapper = new QueryWrapper<>();
		Long current = provinceHandlePageBo.getCurrent();
		Long size = provinceHandlePageBo.getSize();
		Integer provinceCode = provinceHandlePageBo.getProvinceCode();
		String provinceName = provinceHandlePageBo.getProvinceName();

		if (provinceCode != null) {
			queryWrapper.eq("province_code", provinceCode);
		}
		if (StringUtils.hasText(provinceName)) {
			queryWrapper.eq("province_name", provinceName);
		}

		IPage<ProvinceHandle> page = new Page<>(current == null ? 1 : current, size == null ? 10 : size);

		return page(page, queryWrapper);
	}

}
