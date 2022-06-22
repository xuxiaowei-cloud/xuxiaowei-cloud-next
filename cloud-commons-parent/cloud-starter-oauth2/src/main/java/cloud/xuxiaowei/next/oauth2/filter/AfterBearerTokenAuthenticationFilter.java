package cloud.xuxiaowei.next.oauth2.filter;

import cloud.xuxiaowei.next.utils.CodeEnums;
import cloud.xuxiaowei.next.utils.Response;
import cloud.xuxiaowei.next.utils.ResponseUtils;
import cloud.xuxiaowei.next.utils.SecurityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * {@link BearerTokenAuthenticationFilter} 之后运行
 *
 * @author xuxiaowei
 * @since 0.0.1
 * @see BearerTokenAuthenticationFilter
 */
@Slf4j
@Component
public class AfterBearerTokenAuthenticationFilter extends OncePerRequestFilter {

	private DataSource dataSource;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {

		// 查看数据库中是否存在此 授权 Token
		String tokenValue = SecurityUtils.getTokenValue();
		Integer integer = new JdbcTemplate(dataSource).queryForObject(
				"SELECT count( 1 ) FROM oauth2_authorization WHERE access_token_value = ?", Integer.class, tokenValue);
		int result = integer == null ? 0 : integer;

		// 根据数据库中是否存在此 授权 Token 来决定是否放行
		if (result > 0) {
			filterChain.doFilter(request, response);
		}
		else {
			// 数据库不存在此 授权 Token，情况当前授权
			SecurityContextHolder.clearContext();

			log.error("数据库中不存在 授权 Token：{}", tokenValue);

			Response<?> error = Response.error(CodeEnums.T10004.code, CodeEnums.T10004.msg);
			ResponseUtils.response(response, error);
		}
	}

}
