package cloud.xuxiaowei.next.gateway.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

/**
 * 客户 配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
public class RegisteredClientConfiguration {

	/**
	 * @see RegisteredClientRepository 用于管理客户端的实例。
	 */
	@Bean
	public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
		return new JdbcRegisteredClientRepository(jdbcTemplate);
	}

	/**
	 * 授权码、授权Token、刷新Token 持久化
	 */
	@Bean
	public OAuth2AuthorizationService oauth2AuthorizationService(JdbcOperations jdbcOperations,
			RegisteredClientRepository registeredClientRepository) {
		JdbcOAuth2AuthorizationService oauth2AuthorizationService = new JdbcOAuth2AuthorizationService(jdbcOperations,
				registeredClientRepository);
		ObjectMapper objectMapper = new ObjectMapper();

		JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper authorizationRowMapper = new JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper(
				registeredClientRepository);
		authorizationRowMapper.setLobHandler(new DefaultLobHandler());

		authorizationRowMapper.setObjectMapper(objectMapper);

		oauth2AuthorizationService.setAuthorizationRowMapper(authorizationRowMapper);

		return oauth2AuthorizationService;
	}

}
