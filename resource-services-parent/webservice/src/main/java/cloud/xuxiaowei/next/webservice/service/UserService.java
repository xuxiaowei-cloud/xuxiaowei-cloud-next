package cloud.xuxiaowei.next.webservice.service;

import cloud.xuxiaowei.next.webservice.bo.UserBo;
import cloud.xuxiaowei.next.webservice.vo.UserVo;

/**
 * 用户 WebService 接口
 *
 * @author 徐晓伟
 */
public interface UserService {

	/**
	 * 根据 用户ID 查询用户
	 * @param userBo 用户ID
	 * @return 返回 用户
	 */
	UserVo getById(UserBo userBo);

}
