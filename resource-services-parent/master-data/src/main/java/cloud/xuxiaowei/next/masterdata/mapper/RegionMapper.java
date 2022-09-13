package cloud.xuxiaowei.next.masterdata.mapper;

import cloud.xuxiaowei.next.masterdata.bo.RegionPageBo;
import cloud.xuxiaowei.next.masterdata.vo.RegionPageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 区域 Mapper 接口
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public interface RegionMapper {

	/**
	 * 分页查询条数
	 * @param regionPageBo 分页参数
	 * @return 返回 分页统计条数
	 */
	long pageCount(@Param("regionPageBo") RegionPageBo regionPageBo);

	/**
	 * 分页查询数据
	 * @param regionPageBo 分页参数
	 * @return 返回 分页查询数据
	 */
	List<RegionPageVo> page(@Param("regionPageBo") RegionPageBo regionPageBo);

}
