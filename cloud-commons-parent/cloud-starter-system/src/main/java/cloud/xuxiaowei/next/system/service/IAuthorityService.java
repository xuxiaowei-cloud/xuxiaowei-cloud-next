package cloud.xuxiaowei.next.system.service;

import cloud.xuxiaowei.next.system.bo.AuthorityBo;
import cloud.xuxiaowei.next.system.bo.AuthorityPageBo;
import cloud.xuxiaowei.next.system.entity.Authority;
import cloud.xuxiaowei.next.system.vo.AuthorityVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 权限与权限说明表 服务类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-05-17
 */
public interface IAuthorityService extends IService<Authority> {

    /**
     * 查询 权限与权限说明表
     *
     * @param authorityBo 权限与权限说明参数
     * @return 返回 查询结果
     */
    List<Authority> listByAuthorityBo(AuthorityBo authorityBo);

    /**
     * 分页查询 权限与权限说明表
     *
     * @param authorityPageBo 权限与权限说明分页参数
     * @return 返回 查询结果
     */
    IPage<Authority> pageByAuthorityPageBo(AuthorityPageBo authorityPageBo);

    /**
     * 根据 用户名 查询权限
     *
     * @param username 用户名
     * @return 返回 权限
     */
    Set<AuthorityVo> listByUsername(String username);

}
