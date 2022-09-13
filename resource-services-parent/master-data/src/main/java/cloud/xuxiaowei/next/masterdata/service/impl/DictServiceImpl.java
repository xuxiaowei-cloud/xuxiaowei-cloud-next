package cloud.xuxiaowei.next.masterdata.service.impl;

import cloud.xuxiaowei.next.masterdata.bo.DictPageBo;
import cloud.xuxiaowei.next.masterdata.bo.DictSaveBo;
import cloud.xuxiaowei.next.masterdata.bo.DictUpdateBo;
import cloud.xuxiaowei.next.masterdata.entity.Dict;
import cloud.xuxiaowei.next.masterdata.mapper.DictMapper;
import cloud.xuxiaowei.next.masterdata.service.IDictService;
import cloud.xuxiaowei.next.masterdata.vo.DictVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-23
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

	/**
	 * 查询字典代码列表
	 * @return 返回查询结果
	 */
	@Override
	public List<DictVo> listDictVo() {
		List<Dict> dictList = list();
		List<DictVo> dictVoList = new ArrayList<>();
		for (Dict dict : dictList) {
			DictVo dictVo = new DictVo();
			BeanUtils.copyProperties(dict, dictVo);
			dictVoList.add(dictVo);
		}
		return dictVoList;
	}

	/**
	 * 分页查询字典
	 * @param dictPageBo 字典分页参数
	 * @return 返回查询结果
	 */
	@Override
	public IPage<Dict> pageByDictPageBo(DictPageBo dictPageBo) {
		QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
		Long current = dictPageBo.getCurrent();
		Long size = dictPageBo.getSize();
		String dictCode = dictPageBo.getDictCode();
		String dictExplain = dictPageBo.getDictExplain();
		Integer redisExpire = dictPageBo.getRedisExpire();
		String gb = dictPageBo.getGb();
		String gbUrl = dictPageBo.getGbUrl();
		String remark = dictPageBo.getRemark();

		if (StringUtils.hasText(dictCode)) {
			queryWrapper.eq("dict_code", dictCode);
		}
		if (StringUtils.hasText(dictExplain)) {
			queryWrapper.eq("dict_explain", dictExplain);
		}
		if (redisExpire != null) {
			queryWrapper.eq("redis_expire", redisExpire);
		}
		if (StringUtils.hasText(gb)) {
			queryWrapper.eq("gb", gb);
		}
		if (StringUtils.hasText(gbUrl)) {
			queryWrapper.eq("gb_url", gbUrl);
		}
		if (StringUtils.hasText(remark)) {
			queryWrapper.eq("remark", remark);
		}

		IPage<Dict> page = new Page<>(current == null ? 1 : current, size == null ? 10 : size);

		return page(page, queryWrapper);
	}

	/**
	 * 保存 字典
	 * @param dictSaveBo 保存字典表参数
	 * @return 返回 保存结果
	 */
	@Override
	public boolean saveByDictSaveBo(DictSaveBo dictSaveBo) {
		Dict dict = new Dict();
		BeanUtils.copyProperties(dictSaveBo, dict);
		return save(dict);
	}

	/**
	 * 更新 字典
	 * @param dictUpdateBo 更新字典表参数
	 * @return 返回 更新结果
	 */
	@Override
	public boolean updateByDictUpdateBo(DictUpdateBo dictUpdateBo) {
		Dict dict = new Dict();
		BeanUtils.copyProperties(dictUpdateBo, dict);
		return updateById(dict);
	}

}
