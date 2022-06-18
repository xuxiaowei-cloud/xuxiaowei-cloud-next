package cloud.xuxiaowei.next.authorizationserver.service.impl;

import cloud.xuxiaowei.next.authorizationserver.entity.Oauth2Authorization;
import cloud.xuxiaowei.next.authorizationserver.mapper.Oauth2AuthorizationMapper;
import cloud.xuxiaowei.next.authorizationserver.service.IOauth2AuthorizationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表。	原表结构：oauth2-authorization-server-*.*.*.jar!/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql	原表结构：https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql	GitCode 镜像仓库：https://gitcode.net/mirrors/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql	 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-06-18
 */
@Service
public class Oauth2AuthorizationServiceImpl extends ServiceImpl<Oauth2AuthorizationMapper, Oauth2Authorization> implements IOauth2AuthorizationService {

}
