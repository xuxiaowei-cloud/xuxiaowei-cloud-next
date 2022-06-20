package cloud.xuxiaowei.next.passport.service;

import cloud.xuxiaowei.next.passport.bo.Oauth2AuthorizationConsentPageBo;
import cloud.xuxiaowei.next.passport.bo.Oauth2AuthorizationConsentPrimaryKey;
import cloud.xuxiaowei.next.passport.entity.Oauth2AuthorizationConsent;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 授权同意书表。	原表结构：oauth2-authorization-server-*.*.*.jar!/org/springframework/security/oauth2/server/authorization/oauth2-authorization-consent-schema.sql	原表结构：https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-consent-schema.sql	GitCode 镜像仓库：https://gitcode.net/mirrors/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-consent-schema.sql	 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-06-18
 */
public interface IOauth2AuthorizationConsentService extends IService<Oauth2AuthorizationConsent> {

    /**
     * 分页查询 授权同意书表
     *
     * @param oauth2AuthorizationConsentPageBo 授权同意书表 分页参数
     * @return 返回 分页结果
     */
    IPage<Oauth2AuthorizationConsent> pageByOauth2AuthorizationConsentPageBo(Oauth2AuthorizationConsentPageBo oauth2AuthorizationConsentPageBo);

    /**
     * 根据联合主键删除授权同意书表
     *
     * @param oauth2AuthorizationConsentPrimaryKey 联合主键
     * @return 返回 删除结果
     */
    boolean removeByPrimaryKey(Oauth2AuthorizationConsentPrimaryKey oauth2AuthorizationConsentPrimaryKey);

    /**
     * 根据联合主键批量删除授权同意书表
     *
     * @param oauth2AuthorizationConsentPrimaryKeys 联合主键
     * @return 返回 删除结果
     */
    boolean removeByPrimaryKeys(List<Oauth2AuthorizationConsentPrimaryKey> oauth2AuthorizationConsentPrimaryKeys);

}
