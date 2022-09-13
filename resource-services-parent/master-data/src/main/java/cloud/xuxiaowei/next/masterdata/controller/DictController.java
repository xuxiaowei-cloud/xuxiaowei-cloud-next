package cloud.xuxiaowei.next.masterdata.controller;

import cloud.xuxiaowei.next.masterdata.bo.DictPageBo;
import cloud.xuxiaowei.next.masterdata.bo.DictSaveBo;
import cloud.xuxiaowei.next.masterdata.bo.DictUpdateBo;
import cloud.xuxiaowei.next.masterdata.entity.Dict;
import cloud.xuxiaowei.next.masterdata.service.IDictService;
import cloud.xuxiaowei.next.masterdata.vo.DictVo;
import cloud.xuxiaowei.next.system.annotation.ControllerAnnotation;
import cloud.xuxiaowei.next.utils.AssertUtils;
import cloud.xuxiaowei.next.utils.Response;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-23
 */
@RestController
@RequestMapping("/dict")
public class DictController {

	private IDictService dictService;

	@Autowired
	public void setDictService(IDictService dictService) {
		this.dictService = dictService;
	}

	/**
	 * 查询字典代码列表
	 * @param request 请求
	 * @param response 响应
	 * @return 返回 查询结果
	 */
	@ControllerAnnotation(description = "查询字典代码列表")
	@PreAuthorize("hasAuthority('dict_read')")
	@RequestMapping("/list")
	public Response<?> list(HttpServletRequest request, HttpServletResponse response) {
		List<DictVo> dictVoList = dictService.listDictVo();
		return Response.ok(dictVoList);
	}

	/**
	 * 根据 字典代码 查询
	 * @param request 请求
	 * @param response 响应
	 * @param dictCode 字典代码
	 * @return 返回 查询结果
	 */
	@ControllerAnnotation(description = "根据 字典代码 查询")
	@PreAuthorize("hasAuthority('dict_read')")
	@RequestMapping("/getById/{dictCode}")
	public Response<?> getById(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("dictCode") String dictCode) {
		Dict dict = dictService.getById(dictCode);
		if (dict == null) {
			return Response.ok((Dict) null);
		}
		DictVo dictVo = new DictVo();
		BeanUtils.copyProperties(dict, dictVo);
		return Response.ok(dictVo);
	}

	/**
	 * 保存 字典
	 * @param request 请求
	 * @param response 响应
	 * @param dictSaveBo 保存字典表参数
	 * @return 返回 结果
	 */
	@ControllerAnnotation(description = "保存 字典")
	@RequestMapping("/save")
	@PreAuthorize("hasAuthority('dict_add')")
	public Response<?> save(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody DictSaveBo dictSaveBo) {

		boolean save = dictService.saveByDictSaveBo(dictSaveBo);

		return Response.ok(save);
	}

	/**
	 * 更新 字典
	 * @param request 请求
	 * @param response 响应
	 * @param dictUpdateBo 更新字典表参数
	 * @return 返回 结果
	 */
	@ControllerAnnotation(description = "更新 字典")
	@RequestMapping("/updateById")
	@PreAuthorize("hasAuthority('dict_edit')")
	public Response<?> updateById(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody DictUpdateBo dictUpdateBo) {

		boolean update = dictService.updateByDictUpdateBo(dictUpdateBo);

		return Response.ok(update);
	}

	/**
	 * 分页查询字典
	 * @param request 请求
	 * @param response 响应
	 * @param dictPageBo 字典分页参数
	 * @return 返回 查询结果
	 */
	@ControllerAnnotation(description = "分页查询字典")
	@PreAuthorize("hasAuthority('dict_read')")
	@RequestMapping("/page")
	public Response<?> page(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody DictPageBo dictPageBo) {
		IPage<Dict> page = dictService.pageByDictPageBo(dictPageBo);
		return Response.ok(page);
	}

	/**
	 * 根据 字典代码 删除
	 * @param request 请求
	 * @param response 响应
	 * @param dictCode 字典代码
	 * @return 返回 删除结果
	 */
	@ControllerAnnotation(description = "根据 字典代码 删除")
	@PreAuthorize("hasAuthority('dict_delete')")
	@RequestMapping("/removeById/{dictCode}")
	public Response<?> removeById(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("dictCode") String dictCode) {

		boolean removeById = dictService.removeById(dictCode);

		return Response.ok(removeById);
	}

	/**
	 * 根据 字典代码 批量删除
	 * @param request 请求
	 * @param response 响应
	 * @param dictCodes 字典代码
	 * @return 返回 删除结果
	 */
	@ControllerAnnotation(description = "根据 字典代码 批量删除")
	@PreAuthorize("hasAuthority('dict_delete')")
	@RequestMapping("/removeByIds")
	public Response<?> removeByIds(HttpServletRequest request, HttpServletResponse response,
			@RequestBody List<String> dictCodes) {

		AssertUtils.sizeNonNull(dictCodes, 1, 50, "非法数据长度");

		boolean removeByIds = dictService.removeByIds(dictCodes);

		return Response.ok(removeByIds);
	}

}
