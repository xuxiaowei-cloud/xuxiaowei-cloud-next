package cloud.xuxiaowei.next.passport.mapper;

import cloud.xuxiaowei.next.passport.entity.Oauth2Authorization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 授权表。	原表结构：oauth2-authorization-server-*.*.*.jar!/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql	原表结构：https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql	GitCode 镜像仓库：https://gitcode.net/mirrors/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql	 Mapper 接口
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-06-18
 */
public interface Oauth2AuthorizationMapper extends BaseMapper<Oauth2Authorization> {

}
