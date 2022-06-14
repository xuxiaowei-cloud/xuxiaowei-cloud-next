package cloud.xuxiaowei.next.system.service;

import cloud.xuxiaowei.next.system.bo.AuthoritiesSaveBo;
import cloud.xuxiaowei.next.system.entity.Authorities;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 权限表。	原表结构：spring-security-core-*.*.*.jar!/org/springframework/security/core/userdetails/jdbc/users.ddl	原表结构：https://github.com/spring-projects/spring-security/blob/main/core/src/main/resources/org/springframework/security/core/userdetails/jdbc/users.ddl	GitCode 镜像仓库：https://gitcode.net/mirrors/spring-projects/spring-security/blob/main/core/src/main/resources/org/springframework/security/core/userdetails/jdbc/users.ddl 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-04-04
 */
public interface IAuthoritiesService extends IService<Authorities> {

    /**
     * 根据 用户名 查询权限
     *
     * @param username 用户名
     * @return 返回 权限
     */
    List<Authorities> listByUsername(String username);

    /**
     * 根据 用户名 查询权限
     *
     * @param username 用户名
     * @return 返回 权限
     */
    Set<String> listAuthorityByUsername(String username);

    /**
     * 根据 用户名、权限 保存
     *
     * @param authoritiesSaveBo 权限表保存参数
     * @return 返回 保存结果
     */
    boolean saveByAuthoritiesSaveBo(AuthoritiesSaveBo authoritiesSaveBo);

    /**
     * 根据 用户名、权限 删除
     *
     * @param username      用户名
     * @param authorityList 权限
     * @return 返回 删除结果
     */
    boolean removeByUsernameAndAuthoritiesList(String username, Set<String> authorityList);

}
