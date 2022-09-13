package cloud.xuxiaowei.next.masterdata.service;

import cloud.xuxiaowei.next.masterdata.bo.ProvinceHandlePageBo;
import cloud.xuxiaowei.next.masterdata.entity.ProvinceHandle;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 省份 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-20
 */
public interface IProvinceHandleService extends IService<ProvinceHandle> {

	/**
	 * 分页查询省份
	 * @param provinceHandlePageBo 分页参数
	 * @return 返回 查询结果
	 */
	IPage<ProvinceHandle> pageByProvinceHandlePageBo(ProvinceHandlePageBo provinceHandlePageBo);

}
