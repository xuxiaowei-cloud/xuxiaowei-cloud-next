package cloud.xuxiaowei.next.user.controller;

import cloud.xuxiaowei.next.system.annotation.ControllerAnnotation;
import cloud.xuxiaowei.next.system.bo.AuthorityBo;
import cloud.xuxiaowei.next.system.bo.AuthorityPageBo;
import cloud.xuxiaowei.next.system.entity.Authority;
import cloud.xuxiaowei.next.system.service.IAuthorityService;
import cloud.xuxiaowei.next.utils.Response;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 权限与权限说明表 前端控制器
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-05-17
 */
@RestController
@RequestMapping("/authority")
public class AuthorityController {

	private IAuthorityService authorityService;

	@Autowired
	public void setAuthorityService(IAuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	/**
	 * 获取 权限与权限说明 字典
	 * @param request 请求
	 * @param response 响应
	 * @param authorityBo 权限与权限说明参数
	 * @return 返回 结果
	 */
	@ControllerAnnotation(description = "获取 权限与权限说明 字典")
	@RequestMapping("/list")
	@PreAuthorize("hasAuthority('manage_user_authority')")
	public Response<?> list(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AuthorityBo authorityBo) {

		List<Authority> authorityList = authorityService.listByAuthorityBo(authorityBo);

		return Response.ok(authorityList);
	}

	/**
	 * 获取 权限与权限说明 分页字典
	 * @param request 请求
	 * @param response 响应
	 * @param authorityPageBo 权限与权限说明分页参数
	 * @return 返回 结果
	 */
	@ControllerAnnotation(description = "获取 权限与权限说明 分页字典")
	@RequestMapping("/page")
	@PreAuthorize("hasAuthority('manage_user_authority')")
	public Response<?> page(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AuthorityPageBo authorityPageBo) {

		IPage<Authority> authorityList = authorityService.pageByAuthorityPageBo(authorityPageBo);

		return Response.ok(authorityList);
	}

}
