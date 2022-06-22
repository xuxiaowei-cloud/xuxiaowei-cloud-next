package cloud.xuxiaowei.next.user.controller;

import cloud.xuxiaowei.next.system.annotation.ControllerAnnotation;
import cloud.xuxiaowei.next.system.bo.ManageUsersPageBo;
import cloud.xuxiaowei.next.system.bo.UsersSaveBo;
import cloud.xuxiaowei.next.system.bo.UsersUpdateBo;
import cloud.xuxiaowei.next.system.service.IUsersService;
import cloud.xuxiaowei.next.system.service.SessionService;
import cloud.xuxiaowei.next.system.vo.UsersVo;
import cloud.xuxiaowei.next.utils.AssertUtils;
import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.Response;
import cloud.xuxiaowei.next.utils.map.ResponseMap;
import cn.hutool.crypto.asymmetric.RSA;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.base.Joiner;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@RestController
public class UserRestController {

	private SessionService sessionService;

	private IUsersService usersService;

	@Autowired
	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	@Autowired
	public void setUsersService(IUsersService usersService) {
		this.usersService = usersService;
	}

	/**
	 * 用户信息
	 * @param request 请求
	 * @param response 响应
	 * @return 返回 结果
	 */
	@ControllerAnnotation(description = "用户信息")
	@PreAuthorize("hasAuthority('user_info')")
	@RequestMapping("/info")
	public Response<?> info(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

		String name = authentication.getName();
		UsersVo usersVo = usersService.getUsersVoByUsername(name);
		if (usersVo == null) {
			return Response.error();
		}

		return Response.ok(usersVo);
	}

	/**
	 * 用户权限
	 * @param request 请求
	 * @param response 响应
	 * @return 返回 结果
	 */
	@ControllerAnnotation(description = "用户权限")
	@PreAuthorize("hasAuthority('user_authorities')")
	@RequestMapping("/authorities")
	public Response<?> authorities(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		return Response.ok(authorities);
	}

	/**
	 * 用户详情
	 * @param request 请求
	 * @param response 响应
	 * @return 返回 结果
	 */
	@ControllerAnnotation(description = "用户详情")
	@PreAuthorize("hasAuthority('user_details')")
	@RequestMapping("/details")
	public Response<?> details(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {

		Object details = authentication.getDetails();

		return Response.ok(details);
	}

	/**
	 * 分页查询用户
	 * @param request 请求
	 * @param response 响应
	 * @param manageUsersPageBo 用户分页参数
	 * @return 返回 分页查询结果
	 */
	@ControllerAnnotation(description = "分页查询用户")
	@PreAuthorize("hasAuthority('manage_user_read')")
	@RequestMapping("/page")
	public Response<?> page(HttpServletRequest request, HttpServletResponse response,
			@RequestBody ManageUsersPageBo manageUsersPageBo) {

		IPage<UsersVo> page = usersService.pageByManageUsers(manageUsersPageBo);

		return Response.ok(page);
	}

	/**
	 * 根据 用户主键 删除 用户
	 * @param request 请求
	 * @param response 响应
	 * @param usersId 用户主键
	 * @return 返回 删除结果
	 */
	@ControllerAnnotation(description = "根据 用户主键 删除 用户")
	@PreAuthorize("hasAuthority('manage_user_delete')")
	@RequestMapping("/removeById/{usersId}")
	public Response<?> removeById(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("usersId") Long usersId) {

		boolean removeById = usersService.removeById(usersId);

		return Response.ok(removeById);
	}

	/**
	 * 根据 用户主键 批量删除 用户
	 * @param request 请求
	 * @param response 响应
	 * @param usersIds 用户主键
	 * @return 返回 删除结果
	 */
	@ControllerAnnotation(description = "根据 用户主键 批量删除 用户")
	@PreAuthorize("hasAuthority('manage_user_delete')")
	@RequestMapping("/removeByIds")
	public Response<?> removeByIds(HttpServletRequest request, HttpServletResponse response,
			@RequestBody List<Long> usersIds) {

		AssertUtils.sizeNonNull(usersIds, 1, 50, "非法数据长度");

		boolean removeByIds = usersService.removeByIds(usersIds);

		return Response.ok(removeByIds);
	}

	/**
	 * 根据 用户主键 查询用户
	 * @param request 请求
	 * @param response 响应
	 * @param usersId 用户主键
	 * @return 返回 查询结果
	 */
	@ControllerAnnotation(description = "根据 用户主键 查询用户")
	@PreAuthorize("hasAuthority('manage_user_read')")
	@RequestMapping("/getById/{usersId}")
	public Response<?> getById(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("usersId") Long usersId) {

		UsersVo usersVo = usersService.getUsersVoById(usersId);

		return Response.ok(usersVo);
	}

	/**
	 * 保存用户
	 * @param request 请求
	 * @param response 响应
	 * @param usersSaveBo 用户
	 * @return 返回 保存结果
	 */
	@ControllerAnnotation(description = "保存用户")
	@PreAuthorize("hasAuthority('manage_user_add')")
	@RequestMapping("/save")
	public Response<?> save(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody UsersSaveBo usersSaveBo) {

		boolean save = usersService.saveUsersSaveBo(usersSaveBo);

		return Response.ok(save);
	}

	/**
	 * 根据 用户主键 更新用户
	 * @param request 请求
	 * @param response 响应
	 * @param usersUpdateBo 用户
	 * @return 返回 更新结果
	 */
	@ControllerAnnotation(description = "根据 用户主键 更新用户")
	@PreAuthorize("hasAuthority('manage_user_edit')")
	@RequestMapping("/updateById")
	public Response<?> updateById(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody UsersUpdateBo usersUpdateBo) {

		boolean update = usersService.updateByUsersUpdateBo(usersUpdateBo);

		return Response.ok(update);
	}

	/**
	 * 获取用户识别码
	 * <p>
	 * 生成RSA密钥对
	 * <p>
	 * 返回识别码
	 * <p>
	 * 返回识别码RSA公钥
	 * <p>
	 * RSA私钥保存到Redis中
	 * <p>
	 * @param request 请求
	 * @param response 响应
	 * @return 返回 更新结果
	 */
	@ControllerAnnotation(description = "获取用户识别码")
	@PreAuthorize("hasAuthority('user_info')")
	@RequestMapping("/code/rsa")
	public Response<?> code(HttpServletRequest request, HttpServletResponse response) {

		RSA generate = new RSA();

		// 获取私钥
		String privateKey = generate.getPrivateKeyBase64();
		// 获取公钥
		String publicKey = generate.getPublicKeyBase64();
		// 识别码
		String code = RandomStringUtils.random(6, Joiner.on("").join(Constant.UPPER_CASE_LIST));

		sessionService.setAttr(Constant.PRIVATE_KEY + ":" + code, privateKey, 1, TimeUnit.HOURS);

		return ResponseMap.ok().put("code", code).put("publicKey", publicKey);
	}

}
