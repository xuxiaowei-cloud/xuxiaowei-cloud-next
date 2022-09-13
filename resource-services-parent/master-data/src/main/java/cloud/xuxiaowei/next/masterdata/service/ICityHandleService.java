package cloud.xuxiaowei.next.masterdata.service;

import cloud.xuxiaowei.next.masterdata.bo.CityHandlePageBo;
import cloud.xuxiaowei.next.masterdata.entity.CityHandle;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 城市 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-20
 */
public interface ICityHandleService extends IService<CityHandle> {

	/**
	 * 分页查询城市
	 * @param cityHandlePageBo 分页参数
	 * @return 返回 查询结果
	 */
	IPage<CityHandle> pageByCityHandlePageBo(CityHandlePageBo cityHandlePageBo);

}
