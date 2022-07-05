package cloud.xuxiaowei.next.passport.controller;

import cloud.xuxiaowei.next.passport.bo.Oauth2RegisteredClientPageBo;
import cloud.xuxiaowei.next.passport.bo.Oauth2RegisteredClientSaveBo;
import cloud.xuxiaowei.next.passport.bo.Oauth2RegisteredClientUpdateBo;
import cloud.xuxiaowei.next.passport.service.IOauth2RegisteredClientService;
import cloud.xuxiaowei.next.passport.vo.Oauth2RegisteredClientVo;
import cloud.xuxiaowei.next.passport.vo.Option;
import cloud.xuxiaowei.next.system.annotation.ControllerAnnotation;
import cloud.xuxiaowei.next.utils.AssertUtils;
import cloud.xuxiaowei.next.utils.Response;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 客户表。
 * 原表结构：oauth2-authorization-server-*.*.*.jar!/org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql
 * 原表结构：https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql
 * GitCode
 * 镜像仓库：https://gitcode.net/mirrors/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql
 * 前端控制器
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-06-18
 */
@RestController
@RequestMapping("/oauth2-registered-client")
public class Oauth2RegisteredClientController {

	public static final String ALGORITHM_SPLIT = "#";

	private IOauth2RegisteredClientService oauth2RegisteredClientService;

	@Autowired
	public void setOauth2RegisteredClientService(IOauth2RegisteredClientService oauth2RegisteredClientService) {
		this.oauth2RegisteredClientService = oauth2RegisteredClientService;
	}

	/**
	 * 授权类型选项
	 * @param request 请求
	 * @param response 响应
	 * @return 返回 授权类型选项
	 */
	@RequestMapping("/grant-type-options")
	@ControllerAnnotation(description = "授权类型选项")
	public Response<?> grantTypeOptions(HttpServletRequest request, HttpServletResponse response) {
		List<Option> list = new ArrayList<>();
		list.add(new Option("authorization_code", "authorization_code"));
		list.add(new Option("refresh_token", "refresh_token"));
		list.add(new Option("client_credentials", "client_credentials"));
		list.add(new Option("password", "password"));
		list.add(new Option("webchat_miniprogram", "webchat_miniprogram"));
		return Response.ok(list);
	}

	/**
	 * 客户端身份验证方法选项
	 * @param request 请求
	 * @param response 响应
	 * @return 返回 客户端身份验证方法选项
	 */
	@RequestMapping("/authentication-method-options")
	@ControllerAnnotation(description = "客户端身份验证方法选项")
	public Response<?> authenticationMethodOptions(HttpServletRequest request, HttpServletResponse response) {
		List<Option> list = new ArrayList<>();
		list.add(new Option("basic", "basic"));
		list.add(new Option("client_secret_basic", "client_secret_basic"));
		list.add(new Option("post", "post"));
		list.add(new Option("client_secret_post", "client_secret_post"));
		list.add(new Option("client_secret_jwt", "client_secret_jwt"));
		list.add(new Option("private_key_jwt", "private_key_jwt"));
		list.add(new Option("none", "none"));
		return Response.ok(list);
	}

	/**
	 * 授权范围选项
	 * @param request 请求
	 * @param response 响应
	 * @return 返回 授权范围选项
	 */
	@RequestMapping("/scope-options")
	@ControllerAnnotation(description = "授权范围选项")
	public Response<?> scopeOptions(HttpServletRequest request, HttpServletResponse response) {
		List<Option> list = new ArrayList<>();
		list.add(new Option("snsapi_base", "snsapi_base"));
		list.add(new Option("snsapi_info", "snsapi_info"));
		return Response.ok(list);
	}

	/**
	 * 令牌端点认证签名算法选项
	 * @param request 请求
	 * @param response 响应
	 * @return 返回 令牌端点认证签名算法选项
	 */
	@RequestMapping("/token-signing-algorithm-options")
	@ControllerAnnotation(description = "令牌端点认证签名算法选项")
	public Response<?> tokenSigningAlgorithmOptions(HttpServletRequest request, HttpServletResponse response) {
		List<Option> list = new ArrayList<>();

		MacAlgorithm[] macAlgorithms = MacAlgorithm.values();
		for (MacAlgorithm value : macAlgorithms) {
			String algorithm = value.getClass().getName() + ALGORITHM_SPLIT + value.getName();
			list.add(new Option(algorithm, algorithm));
		}

		SignatureAlgorithm[] signatureAlgorithms = SignatureAlgorithm.values();
		for (SignatureAlgorithm value : signatureAlgorithms) {
			String algorithm = value.getClass().getName() + ALGORITHM_SPLIT + value.getName();
			list.add(new Option(algorithm, algorithm));
		}

		return Response.ok(list);
	}

	/**
	 * id 令牌签名算法选项
	 * @param request 请求
	 * @param response 响应
	 * @return 返回 id 令牌签名算法选项
	 */
	@RequestMapping("/token-signature-algorithm-options")
	@ControllerAnnotation(description = "id 令牌签名算法选项")
	public Response<?> tokenSignatureAlgorithmOptions(HttpServletRequest request, HttpServletResponse response) {
		List<Option> list = new ArrayList<>();

		SignatureAlgorithm[] signatureAlgorithms = SignatureAlgorithm.values();
		for (SignatureAlgorithm value : signatureAlgorithms) {
			String algorithm = value.getClass().getName() + ALGORITHM_SPLIT + value.getName();
			list.add(new Option(algorithm, algorithm));
		}

		return Response.ok(list);
	}

	/**
	 * 分页查询客户
	 * @param request 请求
	 * @param response 响应
	 * @param oauth2RegisteredClientPageBo 客户分页参数
	 * @return 返回 分页查询结果
	 */
	@ControllerAnnotation(description = "分页查询客户")
	@PreAuthorize("hasAuthority('manage_client_read')")
	@RequestMapping("/page")
	public Response<?> page(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody Oauth2RegisteredClientPageBo oauth2RegisteredClientPageBo) {

		IPage<Oauth2RegisteredClientVo> page = oauth2RegisteredClientService
				.pageByOauth2RegisteredClientPageBo(oauth2RegisteredClientPageBo);

		return Response.ok(page);
	}

	/**
	 * 根据 客户主键 删除 授权码
	 * @param request 请求
	 * @param response 响应
	 * @param id 客户主键
	 * @return 返回 删除结果
	 */
	@ControllerAnnotation(description = "根据 客户主键 删除 授权码")
	@PreAuthorize("hasAuthority('manage_client_delete')")
	@RequestMapping("/removeById/{id}")
	public Response<?> removeById(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") String id) {

		boolean removeById = oauth2RegisteredClientService.removeById(id);

		return Response.ok(removeById);
	}

	/**
	 * 根据 客户主键 批量删除 授权码
	 * @param request 请求
	 * @param response 响应
	 * @param ids 客户主键
	 * @return 返回 删除结果
	 */
	@ControllerAnnotation(description = "根据 客户主键 批量删除 授权码")
	@PreAuthorize("hasAuthority('manage_client_delete')")
	@RequestMapping("/removeByIds")
	public Response<?> removeByIds(HttpServletRequest request, HttpServletResponse response,
			@RequestBody List<String> ids) {

		AssertUtils.sizeNonNull(ids, 1, 50, "非法数据长度");

		boolean removeByIds = oauth2RegisteredClientService.removeByIds(ids);

		return Response.ok(removeByIds);
	}

	/**
	 * 根据 客户主键 查询客户
	 * @param request 请求
	 * @param response 响应
	 * @param id 客户主键
	 * @return 返回 查询结果
	 */
	@ControllerAnnotation(description = "根据 客户主键 查询客户")
	@PreAuthorize("hasAuthority('manage_client_read')")
	@RequestMapping("/getById/{id}")
	public Response<?> getById(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") String id) {

		Oauth2RegisteredClientVo oauth2RegisteredClientVo = oauth2RegisteredClientService.getVoById(id);

		return Response.ok(oauth2RegisteredClientVo);
	}

	/**
	 * 保存客户
	 * @param request 请求
	 * @param response 响应
	 * @param oauth2RegisteredClientSaveBo 客户 保存参数
	 * @return 返回 保存结果
	 */
	@ControllerAnnotation(description = "保存客户")
	@PreAuthorize("hasAuthority('manage_client_add')")
	@RequestMapping("/save")
	public Response<?> save(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody Oauth2RegisteredClientSaveBo oauth2RegisteredClientSaveBo) {

		boolean save = oauth2RegisteredClientService.saveOauth2RegisteredClientSaveBo(oauth2RegisteredClientSaveBo);

		return Response.ok(save);
	}

	/**
	 * 根据 客户主键 更新客户
	 * @param request 请求
	 * @param response 响应
	 * @param oauth2RegisteredClientUpdateBo 客户 更新参数
	 * @return 返回 更新结果
	 */
	@ControllerAnnotation(description = "根据 客户主键 更新客户")
	@PreAuthorize("hasAuthority('manage_client_edit')")
	@RequestMapping("/updateById")
	public Response<?> updateById(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody Oauth2RegisteredClientUpdateBo oauth2RegisteredClientUpdateBo) {

		boolean update = oauth2RegisteredClientService
				.updateByOauth2RegisteredClientUpdateBo(oauth2RegisteredClientUpdateBo);

		return Response.ok(update);
	}

}
