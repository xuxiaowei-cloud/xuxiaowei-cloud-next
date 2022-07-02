package cloud.xuxiaowei.next.user.controller;

import cloud.xuxiaowei.next.system.annotation.ControllerAnnotation;
import cloud.xuxiaowei.next.system.bo.AuthoritiesSaveBo;
import cloud.xuxiaowei.next.system.service.IAuthoritiesService;
import cloud.xuxiaowei.next.utils.Response;
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
 * 权限表 前端控制器
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-05-17
 */
@RestController
@RequestMapping("/authorities")
public class AuthoritiesController {

	private IAuthoritiesService authoritiesService;

	@Autowired
	public void setAuthoritiesService(IAuthoritiesService authoritiesService) {
		this.authoritiesService = authoritiesService;
	}

	/**
	 * 保存 权限表
	 * @param request 请求
	 * @param response 响应
	 * @param authoritiesSaveBo 权限表保存参数
	 * @return 返回 结果
	 */
	@ControllerAnnotation(description = "保存 权限表")
	@RequestMapping("/save")
	@PreAuthorize("hasAuthority('manage_user_authority')")
	public Response<?> save(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody AuthoritiesSaveBo authoritiesSaveBo) {

		boolean save = authoritiesService.saveByAuthoritiesSaveBo(authoritiesSaveBo);

		return Response.ok(save);
	}

}
