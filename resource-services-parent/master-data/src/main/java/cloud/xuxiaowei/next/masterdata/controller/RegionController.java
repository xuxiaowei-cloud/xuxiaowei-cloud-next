package cloud.xuxiaowei.next.masterdata.controller;

import cloud.xuxiaowei.next.masterdata.bo.RegionPageBo;
import cloud.xuxiaowei.next.masterdata.service.RegionService;
import cloud.xuxiaowei.next.masterdata.vo.RegionPageVo;
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
 * 区域 前端控制器
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-20
 */
@RestController
@RequestMapping("/region")
public class RegionController {

	private RegionService regionService;

	@Autowired
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	/**
	 * 分页查询区域
	 * @param request 请求
	 * @param response 响应
	 * @param regionPageBo 分页参数
	 * @return 返回 查询结果
	 */
	@PreAuthorize("hasAnyAuthority('region_read', 'user_info')")
	@RequestMapping("/page")
	public Response<?> page(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody RegionPageBo regionPageBo) {
		IPage<RegionPageVo> page = regionService.page(regionPageBo);
		return Response.ok(page);
	}

}
