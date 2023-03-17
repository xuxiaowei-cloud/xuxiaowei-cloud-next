package cloud.xuxiaowei.next.example.cxfclient.service;

import cloud.xuxiaowei.next.example.cxfclient.bo.UserBo;
import cloud.xuxiaowei.next.example.cxfclient.vo.ResponseUserVo;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;

/**
 * 用户 WebService 接口-客户端
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@WebService(targetNamespace = "http://webservice.xuxiaowei.cloud")
public interface UserServiceClient {

	/**
	 * 根据 用户ID 查询用户
	 * @param userBo 用户ID
	 * @return 返回 用户
	 */
	@WebMethod
	@WebResult(name = "response")
	ResponseUserVo getById(@WebParam(name = "request") UserBo userBo);

}
