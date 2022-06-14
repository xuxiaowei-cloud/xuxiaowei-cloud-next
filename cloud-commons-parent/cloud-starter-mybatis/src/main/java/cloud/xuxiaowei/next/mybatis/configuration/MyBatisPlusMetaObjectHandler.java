package cloud.xuxiaowei.next.mybatis.configuration;


import cloud.xuxiaowei.next.utils.Constant;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * Mybatis Plus 自动填充
 *
 * @author 徐晓伟
 * @since 0.0.1
 */
@Slf4j
@Configuration
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        strictInsertFill(metaObject, "createDate", LocalDateTime.class, LocalDateTime.now());
        strictInsertFill(metaObject, "createUsername", String.class, username());
        strictInsertFill(metaObject, "createIp", String.class, ip());
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        strictUpdateFill(metaObject, "updateDate", LocalDateTime.class, LocalDateTime.now());
        strictUpdateFill(metaObject, "updateUsername", String.class, username());
        strictUpdateFill(metaObject, "updateIp", String.class, ip());
    }

    private String username() {
        String username = MDC.get(Constant.NAME);
        return username == null ? "" : username;
    }

    private String ip() {
        String ip = MDC.get(Constant.IP);
        return ip == null ? "" : ip;
    }

}
