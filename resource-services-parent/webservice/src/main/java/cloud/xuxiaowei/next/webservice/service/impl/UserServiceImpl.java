package cloud.xuxiaowei.next.webservice.service.impl;

import cloud.xuxiaowei.next.webservice.bo.UserBo;
import cloud.xuxiaowei.next.webservice.service.UserService;
import cloud.xuxiaowei.next.webservice.vo.UserVo;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;
import org.springframework.stereotype.Service;

/**
 * 用户 WebService 接口
 *
 * @author 徐晓伟
 */
@Service
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@WebService(targetNamespace = "http://webservice.xuxiaowei.cloud")
public class UserServiceImpl implements UserService {

	/**
	 * 根据 用户ID 查询用户
	 * @param userBo 用户ID
	 * @return 返回 用户
	 */
	@Override
	@WebMethod
	@WebResult(name = "response")
	public UserVo getById(@WebParam(name = "request") UserBo userBo) {
		UserVo userVo = new UserVo();

		String id = userBo.getId();

		userVo.setId(id);
		userVo.setUsername("用户-" + id);
		userVo.setPassword("用户-" + id + "-密码");
		return userVo;
	}

}
