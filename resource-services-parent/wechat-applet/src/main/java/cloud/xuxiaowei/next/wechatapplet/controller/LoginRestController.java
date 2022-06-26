package cloud.xuxiaowei.next.wechatapplet.controller;

import cloud.xuxiaowei.next.system.annotation.ControllerAnnotation;
import cloud.xuxiaowei.next.system.service.IWxMaUsersService;
import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.Response;
import cloud.xuxiaowei.next.utils.map.ResponseMap;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@RestController
public class LoginRestController {

	private WxMaService wxMaService;

	private IWxMaUsersService wxMaUsersService;

	@Autowired
	public void setWxMaService(WxMaService wxMaService) {
		this.wxMaService = wxMaService;
	}

	@Autowired
	public void setWxMaUsersService(IWxMaUsersService wxMaUsersService) {
		this.wxMaUsersService = wxMaUsersService;
	}

	/**
	 * 微信小程序登录
	 * @param request 请求
	 * @param response 响应
	 * @param code 微信小程序登录code
	 * @return 返回登录结果
	 */
	@ControllerAnnotation(description = "微信小程序登录")
	@RequestMapping("/onLogin")
	public Response<?> onLogin(HttpServletRequest request, HttpServletResponse response, String code) {

		WxMaJscode2SessionResult wxMaJscode2SessionResult;
		try {
			wxMaJscode2SessionResult = wxMaService.jsCode2SessionInfo(code);
		}
		catch (WxErrorException e) {
			log.error("微信小程序登录失败", e);
			return Response.error("登录失败");
		}

		WxMaConfig wxMaConfig = wxMaService.getWxMaConfig();
		String appid = wxMaConfig.getAppid();
		String openid = wxMaJscode2SessionResult.getOpenid();
		String unionid = wxMaJscode2SessionResult.getUnionid();

		MDC.put(Constant.NAME, openid);

		boolean save = wxMaUsersService.saveOpenid(appid, openid, unionid);
		log.info("微信小程序 {} 是否新增用户 {}：{}", appid, openid, save);

		return ResponseMap.ok().put(Constant.APPID, appid).put(Constant.OPENID, openid).put(Constant.UNIONID, unionid);
	}

}
