package cloud.xuxiaowei.next.masterdata.mapper;

import cloud.xuxiaowei.next.masterdata.entity.DictData;
import cloud.xuxiaowei.next.masterdata.vo.DictDataVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字典数据表
 * <p>
 * 为何本表不使用自增主键？
 * <p>
 * 答：考虑到开发环境添加数据、正式环境添加数据、开发环境向正式环境同步字典数据等情况，相同的字典代码与字典数据代码可能对应不同的自增主键，故放弃自增主键，改用字典代码与字典数据代码作为联合主键。
 * <p>
 * 本表external_code与external_label开头的字段有何作用？
 * <p>
 * 答：对接外部系统时，如果外部系统与本系统针对某一字典值、名称不同时，在此增加两列做对照，并在字段中在增加对外部系统的描述，使用时，直接取不同系统对照的不同字段即可
 * Mapper 接口
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-23
 */
public interface DictDataMapper extends BaseMapper<DictData> {

	/**
	 * 根据字典代码查询字典列表
	 * @param dictCode 字典代码
	 * @return 返回 查询结果
	 */
	List<DictDataVo> listByDictCode(@Param("dictCode") String dictCode);

}
