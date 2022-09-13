package cloud.xuxiaowei.next.masterdata.controller;

import cloud.xuxiaowei.next.masterdata.bo.ProvinceHandlePageBo;
import cloud.xuxiaowei.next.masterdata.entity.ProvinceHandle;
import cloud.xuxiaowei.next.masterdata.service.IProvinceHandleService;
import cloud.xuxiaowei.next.utils.Response;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 省份 前端控制器
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-20
 */
@RestController
@RequestMapping("/province-handle")
public class ProvinceHandleController {

	private IProvinceHandleService provinceHandleService;

	@Autowired
	public void setProvinceHandleService(IProvinceHandleService provinceHandleService) {
		this.provinceHandleService = provinceHandleService;
	}

	/**
	 * 分页查询省份
	 * @param request 请求
	 * @param response 响应
	 * @param provinceHandlePageBo 分页参数
	 * @return 返回 查询结果
	 */
	@PreAuthorize("hasAnyAuthority('region_read', 'user_info')")
	@RequestMapping("/page")
	public Response<?> page(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody ProvinceHandlePageBo provinceHandlePageBo) {
		IPage<ProvinceHandle> page = provinceHandleService.pageByProvinceHandlePageBo(provinceHandlePageBo);
		return Response.ok(page);
	}

}
