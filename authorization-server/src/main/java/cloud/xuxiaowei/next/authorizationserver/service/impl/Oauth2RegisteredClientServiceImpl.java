package cloud.xuxiaowei.next.authorizationserver.service.impl;

import cloud.xuxiaowei.next.authorizationserver.entity.Oauth2RegisteredClient;
import cloud.xuxiaowei.next.authorizationserver.mapper.Oauth2RegisteredClientMapper;
import cloud.xuxiaowei.next.authorizationserver.service.IOauth2RegisteredClientService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表。	原表结构：oauth2-authorization-server-*.*.*.jar!/org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql	原表结构：https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql	GitCode 镜像仓库：https://gitcode.net/mirrors/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql	 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-06-18
 */
@Service
public class Oauth2RegisteredClientServiceImpl extends ServiceImpl<Oauth2RegisteredClientMapper, Oauth2RegisteredClient> implements IOauth2RegisteredClientService {

}
