package cloud.xuxiaowei.next.authorizationserver.service;

import cloud.xuxiaowei.next.authorizationserver.bo.Oauth2AuthorizationPageBo;
import cloud.xuxiaowei.next.authorizationserver.entity.Oauth2Authorization;
import cloud.xuxiaowei.next.authorizationserver.vo.Oauth2AuthorizationVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 授权表。	原表结构：oauth2-authorization-server-*.*.*.jar!/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql	原表结构：https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql	GitCode 镜像仓库：https://gitcode.net/mirrors/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql	 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-06-18
 */
public interface IOauth2AuthorizationService extends IService<Oauth2Authorization> {

    /**
     * 分页查询
     *
     * @param oauth2AuthorizationPageBo 授权表 分页参数
     * @return 返回 分页结果
     */
    IPage<Oauth2AuthorizationVo> pageByOauth2AuthorizationPageBo(Oauth2AuthorizationPageBo oauth2AuthorizationPageBo);

}
