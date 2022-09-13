package cloud.xuxiaowei.next.masterdata.service;

import cloud.xuxiaowei.next.masterdata.bo.CountyHandlePageBo;
import cloud.xuxiaowei.next.masterdata.entity.CountyHandle;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 县 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-20
 */
public interface ICountyHandleService extends IService<CountyHandle> {

	/**
	 * 分页查询县
	 * @param countyHandlePageBo 分页参数
	 * @return 返回 查询结果
	 */
	IPage<CountyHandle> pageByCountyHandlePageBo(CountyHandlePageBo countyHandlePageBo);

}
