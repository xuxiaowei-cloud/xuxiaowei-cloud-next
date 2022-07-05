package cloud.xuxiaowei.next.passport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;

import java.util.List;
import java.util.Map;

/**
 * 客户配置 测试类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
class ClientSettingsTests {

	@Test
	void settings() throws JsonProcessingException {
		ClientSettings.Builder clientSettingsBuilder = ClientSettings.builder();

		// 如果客户端请求访问时需要授权同意，则设置为true 。这适用于所有交互流程（例如authorization_code和device_code ）。
		clientSettingsBuilder.requireAuthorizationConsent(true);

		ClientSettings clientSettings = clientSettingsBuilder.build();
		Map<String, Object> settings = clientSettings.getSettings();

		ObjectMapper objectMapper = new ObjectMapper();
		ClassLoader classLoader = JdbcRegisteredClientRepository.class.getClassLoader();
		List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
		objectMapper.registerModules(securityModules);
		objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());

		ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
		String s = objectWriter.writeValueAsString(settings);
		log.info("\n{}", s);
	}

}
