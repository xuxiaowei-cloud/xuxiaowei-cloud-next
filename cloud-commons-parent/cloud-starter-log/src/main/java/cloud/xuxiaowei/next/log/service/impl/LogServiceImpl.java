package cloud.xuxiaowei.next.log.service.impl;

import cloud.xuxiaowei.next.log.entity.Log;
import cloud.xuxiaowei.next.log.mapper.LogMapper;
import cloud.xuxiaowei.next.log.service.ILogService;
import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.exception.ExceptionUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-04-01
 */
@Service
@DS("log")
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

	private ConfigurableEnvironment environment;

	private ConsulDiscoveryProperties consulDiscoveryProperties;

	private ServerProperties serverProperties;

	@Autowired
	public void setEnvironment(ConfigurableEnvironment environment) {
		this.environment = environment;
	}

	@Autowired
	public void setConsulDiscoveryProperties(ConsulDiscoveryProperties consulDiscoveryProperties) {
		this.consulDiscoveryProperties = consulDiscoveryProperties;
	}

	@Autowired
	public void setServerProperties(ServerProperties serverProperties) {
		this.serverProperties = serverProperties;
	}

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
	@Override
	public void saveLog(String hostAddress, String requestId, String sessionId, String method, String requestUri,
			String queryString, String headersMap, String userAgent, Throwable ex) {

		String module = environment.getProperty("spring.application.name");
		String ipAddress = consulDiscoveryProperties.getIpAddress();
		String hostname = consulDiscoveryProperties.getHostname();
		Integer port = serverProperties.getPort();

		LocalDate localDate = LocalDate.now();
		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		int day = localDate.getDayOfMonth();

		LocalTime localTime = LocalTime.now();
		int hour = localTime.getHour();

		Log log = new Log();
		log.setModule(module);
		log.setDate(localDate);
		log.setYear(year);
		log.setMonth(month);
		log.setDay(day);
		log.setHour(hour);
		log.setMethod(method);
		log.setRequestUri(requestUri);
		log.setQueryString(queryString);
		log.setHeadersMap(headersMap);
		log.setUserAgent(userAgent);
		log.setRequestId(requestId);
		log.setSessionId(sessionId);
		log.setException(ex == null ? null : ExceptionUtils.getStackTrace(ex));
		log.setCreateUsername(null);
		log.setCreateIp(hostAddress);
		log.setHostname(hostname);
		log.setIpAddress(ipAddress);
		log.setPort(port);
		log.setCreateDate(LocalDateTime.now());

		save(log);

		Long logId = log.getLogId();
		MDC.put(Constant.LOG_ID, logId + "");
	}

	/**
	 * 根据 主键 设置 创建用户
	 * @param createUsername 创建用户
	 * @param logId 主键
	 */
	@Override
	public void setCreateUsernameById(String createUsername, String logId) {
		UpdateWrapper<Log> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("log_id", logId);
		updateWrapper.set("create_username", createUsername);
		update(updateWrapper);
	}

}
