package cloud.xuxiaowei.next.passport.controller;

import cloud.xuxiaowei.next.passport.bo.Oauth2AuthorizationPageBo;
import cloud.xuxiaowei.next.passport.service.IOauth2AuthorizationService;
import cloud.xuxiaowei.next.passport.vo.Oauth2AuthorizationVo;
import cloud.xuxiaowei.next.system.annotation.ControllerAnnotation;
import cloud.xuxiaowei.next.utils.AssertUtils;
import cloud.xuxiaowei.next.utils.Response;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 授权表。
 * 原表结构：oauth2-authorization-server-*.*.*.jar!/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql
 * 原表结构：https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql
 * GitCode
 * 镜像仓库：https://gitcode.net/mirrors/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql
 * 前端控制器
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-06-18
 */
@RestController
@RequestMapping("/oauth2-authorization")
public class Oauth2AuthorizationController {

	private IOauth2AuthorizationService oauth2AuthorizationService;

	@Autowired
	public void setOauth2AuthorizationService(IOauth2AuthorizationService oauth2AuthorizationService) {
		this.oauth2AuthorizationService = oauth2AuthorizationService;
	}

	/**
	 * 分页查询授权
	 * @param request 请求
	 * @param response 响应
	 * @param oauth2AuthorizationPageBo 授权分页参数
	 * @return 返回 分页查询结果
	 */
	@ControllerAnnotation(description = "分页查询授权")
	@PreAuthorize("hasAuthority('audit_authorization_read')")
	@RequestMapping("/page")
	public Response<?> page(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody Oauth2AuthorizationPageBo oauth2AuthorizationPageBo) {

		IPage<Oauth2AuthorizationVo> page = oauth2AuthorizationService
				.pageByOauth2AuthorizationPageBo(oauth2AuthorizationPageBo);

		return Response.ok(page);
	}

	/**
	 * 根据 主键 删除 授权表
	 * @param request 请求
	 * @param response 响应
	 * @param id 主键
	 * @return 返回 删除结果
	 */
	@ControllerAnnotation(description = "根据 主键 删除 授权表")
	@PreAuthorize("hasAuthority('audit_authorization_delete')")
	@RequestMapping("/removeById/{id}")
	public Response<?> removeById(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") String id) {

		boolean removeById = oauth2AuthorizationService.removeById(id);

		return Response.ok(removeById);
	}

	/**
	 * 根据 主键 批量删除 授权表
	 * @param request 请求
	 * @param response 响应
	 * @param ids 主键
	 * @return 返回 删除结果
	 */
	@ControllerAnnotation(description = "根据 主键 批量删除 授权表")
	@PreAuthorize("hasAuthority('audit_authorization_delete')")
	@RequestMapping("/removeByIds")
	public Response<?> removeByIds(HttpServletRequest request, HttpServletResponse response,
			@RequestBody List<String> ids) {

		AssertUtils.sizeNonNull(ids, 1, 50, "非法数据长度");

		boolean removeByIds = oauth2AuthorizationService.removeByIds(ids);

		return Response.ok(removeByIds);
	}

}
