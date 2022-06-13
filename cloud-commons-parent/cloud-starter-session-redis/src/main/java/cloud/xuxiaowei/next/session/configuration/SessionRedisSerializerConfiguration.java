package cloud.xuxiaowei.next.session.configuration;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

/**
 * Session Redis 配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
@EnableRedisHttpSession
public class SessionRedisSerializerConfiguration {

    /**
     * Spring {@link HttpSession} 默认 Redis 序列化程序
     * <p>
     * 名称必须为：springSessionDefaultRedisSerializer
     *
     * @param redisTemplate Redis 模板
     * @return 返回 Spring {@link HttpSession} 默认 Redis 序列化程序
     * @see RedisHttpSessionConfiguration#setDefaultRedisSerializer(RedisSerializer) 自定义 Spring {@link HttpSession} 默认 Redis 序列化程序
     */
//    @Bean
//    public RedisSerializer<?> springSessionDefaultRedisSerializer(RedisTemplate<?, ?> redisTemplate) {
//        return redisTemplate.getValueSerializer();
//    }

}
