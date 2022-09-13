package cloud.xuxiaowei.next.masterdata.service;

import cloud.xuxiaowei.next.masterdata.bo.VillageHandlePageBo;
import cloud.xuxiaowei.next.masterdata.entity.VillageHandle;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 居委会 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-20
 */
public interface IVillageHandleService extends IService<VillageHandle> {

	/**
	 * 分页查询居委会
	 * @param villageHandlePageBo 分页参数
	 * @return 返回 查询结果
	 */
	IPage<VillageHandle> pageByVillageHandlePageBo(VillageHandlePageBo villageHandlePageBo);

}
