package cloud.xuxiaowei.next.authorizationserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import java.time.Duration;
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

        TokenSettings tokenSettings = tokenSettingsBuilder.build();
        Map<String, Object> settings = tokenSettings.getSettings();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        String s = objectWriter.writeValueAsString(settings);
        log.info("\n{}", s);
    }

}
