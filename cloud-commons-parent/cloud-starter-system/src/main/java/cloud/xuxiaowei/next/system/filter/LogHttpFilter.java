package cloud.xuxiaowei.next.system.filter;

import cloud.xuxiaowei.next.log.service.ILogService;
import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.RequestUtils;
import cloud.xuxiaowei.next.utils.SecurityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * 日志 过滤器
 * <p>
 * 用户在日志输出时，动态添加指定的信息，如：用户唯一标识，IP等
 * <p>
 * 最高优先级
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Order(HIGHEST_PRECEDENCE)
@Component
public class LogHttpFilter extends HttpFilter {

	private ILogService logService;

	@Autowired
	public void setLogService(ILogService logService) {
		this.logService = logService;
	}

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		String remoteHost = req.getRemoteHost();
		MDC.put(Constant.IP, remoteHost);

		String authorization = RequestUtils.getAuthorization(req);
		Map<String, String> payloadMap = SecurityUtils.getPayloadStringMap(authorization);
		String usersId = payloadMap.get(Constant.USERS_ID);
		MDC.put(Constant.USERS_ID, usersId);

		String username = payloadMap.get(Constant.USERNAME);
		String sub = payloadMap.get(JwtClaimNames.SUB);
		if (StringUtils.hasText(username)) {
			MDC.put(Constant.NAME, username);
		}
		else if (StringUtils.hasText(sub)) {
			MDC.put(Constant.NAME, sub);
		}

		String requestId = req.getHeader(Constant.REQUEST_ID);
		if (requestId == null) {
			requestId = UUID.randomUUID().toString();
			MDC.put(Constant.REQUEST_ID, requestId);
		}
		else {
			MDC.put(Constant.REQUEST_ID, requestId);
		}

		HttpSession session = req.getSession();
		String sessionId = session.getId();

		String method = req.getMethod();
		String requestUri = req.getRequestURI();
		String queryString = req.getQueryString();
		String headersMap = RequestUtils.getHeadersJson(req);
		String userAgent = RequestUtils.getUserAgent(req);

		logService.saveLog(remoteHost, requestId, sessionId, method, requestUri, queryString, headersMap, authorization,
				userAgent, null);

		super.doFilter(req, res, chain);
	}

}
