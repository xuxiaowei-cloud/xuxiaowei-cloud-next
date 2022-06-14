package cloud.xuxiaowei.next.system.filter;

import cloud.xuxiaowei.next.utils.Constant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
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

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        String remoteHost = req.getRemoteHost();
        MDC.put(Constant.IP, remoteHost);

        String requestId = req.getHeader(Constant.REQUEST_ID);
        if (requestId == null) {
            MDC.put(Constant.REQUEST_ID, UUID.randomUUID().toString());
        } else {
            MDC.put(Constant.REQUEST_ID, requestId);
        }

        super.doFilter(req, res, chain);
    }

}
