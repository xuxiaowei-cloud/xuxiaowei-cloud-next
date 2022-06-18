package cloud.xuxiaowei.next.authorizationserver.service;

import cloud.xuxiaowei.next.authorizationserver.entity.Oauth2AuthorizationConsent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 权限表。	原表结构：oauth2-authorization-server-*.*.*.jar!/org/springframework/security/oauth2/server/authorization/oauth2-authorization-consent-schema.sql	原表结构：https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-consent-schema.sql	GitCode 镜像仓库：https://gitcode.net/mirrors/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-consent-schema.sql	 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-06-18
 */
public interface IOauth2AuthorizationConsentService extends IService<Oauth2AuthorizationConsent> {

}
