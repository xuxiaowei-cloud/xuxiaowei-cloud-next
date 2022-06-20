package cloud.xuxiaowei.next.passport.service;

import cloud.xuxiaowei.next.passport.bo.Oauth2RegisteredClientPageBo;
import cloud.xuxiaowei.next.passport.bo.Oauth2RegisteredClientSaveBo;
import cloud.xuxiaowei.next.passport.bo.Oauth2RegisteredClientUpdateBo;
import cloud.xuxiaowei.next.passport.entity.Oauth2RegisteredClient;
import cloud.xuxiaowei.next.passport.vo.Oauth2RegisteredClientVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 客户表。	原表结构：oauth2-authorization-server-*.*.*.jar!/org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql	原表结构：https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql	GitCode 镜像仓库：https://gitcode.net/mirrors/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql	 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-06-18
 */
public interface IOauth2RegisteredClientService extends IService<Oauth2RegisteredClient> {

    /**
     * 分页查询
     *
     * @param oauth2RegisteredClientPageBo 客户表 分页参数
     * @return 返回 分页结果
     */
    IPage<Oauth2RegisteredClientVo> pageByOauth2RegisteredClientPageBo(Oauth2RegisteredClientPageBo oauth2RegisteredClientPageBo);

    /**
     * 根据 主键 查询
     *
     * @param id 主键
     * @return 返回 查询结果
     */
    Oauth2RegisteredClientVo getVoById(String id);

    /**
     * 保存 客户
     *
     * @param oauth2RegisteredClientSaveBo 客户表 保存参数
     * @return 返回 保存结果
     */
    boolean saveOauth2RegisteredClientSaveBo(Oauth2RegisteredClientSaveBo oauth2RegisteredClientSaveBo);

    /**
     * 根据主键更新客户表
     *
     * @param oauth2RegisteredClientUpdateBo 客户表 更新参数
     * @return 返回 更新结果
     */
    boolean updateByOauth2RegisteredClientUpdateBo(Oauth2RegisteredClientUpdateBo oauth2RegisteredClientUpdateBo);

}
