package cloud.xuxiaowei.next.masterdata.service;

import cloud.xuxiaowei.next.masterdata.bo.DictDataPageBo;
import cloud.xuxiaowei.next.masterdata.bo.DictDataPrimaryKey;
import cloud.xuxiaowei.next.masterdata.bo.DictDataSaveBo;
import cloud.xuxiaowei.next.masterdata.bo.DictDataUpdateBo;
import cloud.xuxiaowei.next.masterdata.entity.DictData;
import cloud.xuxiaowei.next.masterdata.vo.DictDataVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

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
 * 答：对接外部系统时，如果外部系统与本系统针对某一字典值、名称不同时，在此增加两列做对照，并在字段中在增加对外部系统的描述，使用时，直接取不同系统对照的不同字段即可 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-23
 */
public interface IDictDataService extends IService<DictData> {

	/**
	 * 根据字典代码查询字典列表
	 * @param dictCode 字典代码
	 * @return 返回 查询结果
	 */
	List<DictDataVo> listByDictCode(String dictCode);

	/**
	 * 分页查询字典数据
	 * @param dictDataPageBo 字典数据分页参数
	 * @return 返回 查询结果
	 */
	IPage<DictData> pageByDictDataPageBo(DictDataPageBo dictDataPageBo);

	/**
	 * 根据 字典数据表联合主键 查询
	 * @param dictDataPrimaryKey 字典数据表联合主键
	 * @return 返回查询结果
	 */
	DictData getByDictDataPrimaryKey(DictDataPrimaryKey dictDataPrimaryKey);

	/**
	 * 保存 字典数据
	 * @param dictDataSaveBo 字典数据表保存参数
	 * @return 返回 保存结果
	 */
	boolean saveByDictDataSaveBo(DictDataSaveBo dictDataSaveBo);

	/**
	 * 更新 字典数据
	 * @param dictDataUpdateBo 字典数据表更新参数
	 * @return 返回 更新结果
	 */
	boolean updateByDictDataUpdateBo(DictDataUpdateBo dictDataUpdateBo);

	/**
	 * 根据 字典数据表联合主键 删除
	 * @param dictDataPrimaryKey 字典数据表联合主键
	 * @return 返回 更新结果
	 */
	boolean removeByDictDataPrimaryKey(DictDataPrimaryKey dictDataPrimaryKey);

	/**
	 * 根据 字典数据表联合主键 删除
	 * @param dictDataPrimaryKeys 字典数据表联合主键
	 * @return 返回 更新结果
	 */
	boolean removeByDictDataPrimaryKeys(List<DictDataPrimaryKey> dictDataPrimaryKeys);

}
