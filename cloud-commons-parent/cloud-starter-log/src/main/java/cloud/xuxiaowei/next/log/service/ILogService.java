package cloud.xuxiaowei.next.log.service;

import cloud.xuxiaowei.next.log.entity.Log;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 日志表 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-04-01
 */
public interface ILogService extends IService<Log> {

	/**
	 * 根据请求保存数据
	 * @param hostAddress IP
	 * @param requestId 请求ID
	 * @param sessionId SessionID
	 * @param method 请求方法
	 * @param requestUri 请求地址
	 * @param queryString 请求参数
	 * @param headersMap 请求头
	 * @param userAgent 浏览器标识
	 * @param ex 异常
	 */
	void saveLog(String hostAddress, String requestId, String sessionId, String method, String requestUri,
			String queryString, String headersMap, String userAgent, Throwable ex);

	/**
	 * 根据 主键 设置 创建用户
	 * @param createUsername 创建用户
	 * @param logId 主键
	 */
	void setCreateUsernameById(String createUsername, String logId);

}
