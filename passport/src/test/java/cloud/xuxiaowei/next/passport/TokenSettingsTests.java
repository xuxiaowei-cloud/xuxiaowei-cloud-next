package cloud.xuxiaowei.next.passport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * 客户Token配置 测试类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
class TokenSettingsTests {

	@Test
	void settings() throws JsonProcessingException {
		TokenSettings.Builder tokenSettingsBuilder = TokenSettings.builder();

		// 授权Token时间
		tokenSettingsBuilder.accessTokenTimeToLive(Duration.ofHours(12));
		// 刷新Token时间
		tokenSettingsBuilder.refreshTokenTimeToLive(Duration.ofDays(30));

		tokenSettingsBuilder.accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED);

		TokenSettings tokenSettings = tokenSettingsBuilder.build();
		Map<String, Object> settings = tokenSettings.getSettings();

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
