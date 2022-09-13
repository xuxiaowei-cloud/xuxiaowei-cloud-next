package cloud.xuxiaowei.next.mybatis.configuration;

import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

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
		strictInsertFill(metaObject, "createUsersId", String.class, usersId());
		strictInsertFill(metaObject, "createIp", String.class, ip());
	}

	@Override
	public void updateFill(MetaObject metaObject) {

		strictUpdateFill(metaObject, "updateDate", LocalDateTime.class, LocalDateTime.now());
		strictUpdateFill(metaObject, "updateUsersId", String.class, usersId());
		strictUpdateFill(metaObject, "updateIp", String.class, ip());
	}

	private String usersId() {
		Long usersId = SecurityUtils.getUsersId();
		String name = SecurityUtils.getUserName();
		if (usersId != null) {
			return usersId + "";
		}
		else if (StringUtils.hasText(name)) {
			return name;
		}
		return "";
	}

	private String ip() {
		String ip = MDC.get(Constant.IP);
		return ip == null ? "" : ip;
	}

}
