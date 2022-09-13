package cloud.xuxiaowei.next.masterdata.controller;

import cloud.xuxiaowei.next.masterdata.bo.CountyHandlePageBo;
import cloud.xuxiaowei.next.masterdata.entity.CountyHandle;
import cloud.xuxiaowei.next.masterdata.service.ICountyHandleService;
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
 * 县 前端控制器
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-20
 */
@RestController
@RequestMapping("/county-handle")
public class CountyHandleController {

	private ICountyHandleService countyHandleService;

	@Autowired
	public void setCountyHandleService(ICountyHandleService countyHandleService) {
		this.countyHandleService = countyHandleService;
	}

	/**
	 * 分页查询县
	 * @param request 请求
	 * @param response 响应
	 * @param countyHandlePageBo 分页参数
	 * @return 返回 查询结果
	 */
	@PreAuthorize("hasAnyAuthority('region_read', 'user_info')")
	@RequestMapping("/page")
	public Response<?> page(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody CountyHandlePageBo countyHandlePageBo) {
		IPage<CountyHandle> page = countyHandleService.pageByCountyHandlePageBo(countyHandlePageBo);
		return Response.ok(page);
	}

}
