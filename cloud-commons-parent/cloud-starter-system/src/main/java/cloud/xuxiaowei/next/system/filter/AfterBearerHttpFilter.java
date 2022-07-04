package cloud.xuxiaowei.next.system.filter;

import cloud.xuxiaowei.next.log.service.ILogService;
import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.SecurityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter
 * 执行之后 过滤器
 * <p>
 * 在日志中添加用户标识
 * <p>
 * 优先级高于 -100（即：数值小于 -100），将无效
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Order(-100)
@Component
public class AfterBearerHttpFilter extends HttpFilter {

	private ILogService logService;

	@Autowired
	public void setLogService(ILogService logService) {
		this.logService = logService;
	}

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		// 将当前用户名放入日志中
		String userName = SecurityUtils.getUserName();
		// 将当前用户ID放入日志中
		String usersId = SecurityUtils.getUsersId();

		MDC.put(Constant.NAME, userName);
		MDC.put(Constant.USERS_ID, usersId);

		if (userName != null) {
			logService.setCreateUsernameById(userName, MDC.get(Constant.LOG_ID));
		}

		super.doFilter(req, res, chain);
	}

}
