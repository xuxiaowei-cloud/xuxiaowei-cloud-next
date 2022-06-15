package cloud.xuxiaowei.next.authorizationserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;

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
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        String s = objectWriter.writeValueAsString(settings);
        log.info("\n{}", s);
    }

}
