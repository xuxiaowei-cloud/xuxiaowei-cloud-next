package cloud.xuxiaowei.next.passport;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;

/**
 * 注册客户 测试类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@SpringBootTest
class RegisteredClientRepositoryTests {

	private JdbcTemplate jdbcTemplate;

	private RegisteredClientRepository registeredClientRepository;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.registeredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
	}

	/**
	 * 创建客户
	 */
	@Test
	void save() {

		RegisteredClient.Builder registeredClientBuilder = RegisteredClient.withId(UUID.randomUUID().toString());
		// 客户ID
		registeredClientBuilder.clientId("xuxiaowei_client_id");
		// 客户凭证
		registeredClientBuilder.clientSecret("{noop}xuxiaowei_client_secret");
		// 客户凭证验证方式
		registeredClientBuilder.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
		registeredClientBuilder.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST);
		// 授权类型
		registeredClientBuilder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
		registeredClientBuilder.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN);
		registeredClientBuilder.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS);
		// 授权成功后重定向地址
		registeredClientBuilder.redirectUri("http://127.0.0.1:1401/code");
		// 授权范围
		registeredClientBuilder.scope("snsapi_base");

		ClientSettings.Builder clientSettingsBuilder = ClientSettings.builder();
		clientSettingsBuilder.requireAuthorizationConsent(true);
		ClientSettings clientSettings = clientSettingsBuilder.build();

		RegisteredClient registeredClient = registeredClientBuilder.clientSettings(clientSettings).build();

		registeredClientRepository.save(registeredClient);
	}

}
