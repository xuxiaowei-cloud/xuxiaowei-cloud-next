package cloud.xuxiaowei.next.masterdata.controller;

import cloud.xuxiaowei.next.masterdata.bo.TownHandlePageBo;
import cloud.xuxiaowei.next.masterdata.entity.TownHandle;
import cloud.xuxiaowei.next.masterdata.service.ITownHandleService;
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
 * 镇 前端控制器
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-08-20
 */
@RestController
@RequestMapping("/town-handle")
public class TownHandleController {

	private ITownHandleService townHandleService;

	@Autowired
	public void setTownHandleService(ITownHandleService townHandleService) {
		this.townHandleService = townHandleService;
	}

	/**
	 * 分页查询镇
	 * @param request 请求
	 * @param response 响应
	 * @param townHandlePageBo 分页参数
	 * @return 返回 查询结果
	 */
	@PreAuthorize("hasAnyAuthority('region_read', 'user_info')")
	@RequestMapping("/page")
	public Response<?> page(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody TownHandlePageBo townHandlePageBo) {
		IPage<TownHandle> page = townHandleService.pageByTownHandlePageBo(townHandlePageBo);
		return Response.ok(page);
	}

}
