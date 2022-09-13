package cloud.xuxiaowei.next.masterdata.controller;

import cloud.xuxiaowei.next.masterdata.bo.VillageHandlePageBo;
import cloud.xuxiaowei.next.masterdata.entity.VillageHandle;
import cloud.xuxiaowei.next.masterdata.service.IVillageHandleService;
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
 * 居委会 前端控制器
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-20
 */
@RestController
@RequestMapping("/village-handle")
public class VillageHandleController {

	private IVillageHandleService villageHandleService;

	@Autowired
	public void setVillageHandleService(IVillageHandleService villageHandleService) {
		this.villageHandleService = villageHandleService;
	}

	/**
	 * 分页查询居委会
	 * @param request 请求
	 * @param response 响应
	 * @param villageHandlePageBo 分页参数
	 * @return 返回 查询结果
	 */
	@PreAuthorize("hasAnyAuthority('region_read', 'user_info')")
	@RequestMapping("/page")
	public Response<?> page(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody VillageHandlePageBo villageHandlePageBo) {
		IPage<VillageHandle> page = villageHandleService.pageByVillageHandlePageBo(villageHandlePageBo);
		return Response.ok(page);
	}

}
