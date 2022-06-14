package cloud.xuxiaowei.next.system.service.impl;

import cloud.xuxiaowei.next.system.bo.AuthorityBo;
import cloud.xuxiaowei.next.system.bo.AuthorityPageBo;
import cloud.xuxiaowei.next.system.entity.Authority;
import cloud.xuxiaowei.next.system.mapper.AuthorityMapper;
import cloud.xuxiaowei.next.system.service.IAuthorityService;
import cloud.xuxiaowei.next.system.vo.AuthorityVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 权限与权限说明表 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-05-17
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements IAuthorityService {

    /**
     * 查询 权限与权限说明表
     *
     * @param authorityBo 权限与权限说明参数
     * @return 返回 查询结果
     */
    @Override
    public List<Authority> listByAuthorityBo(AuthorityBo authorityBo) {
        String authority = authorityBo.getAuthority();
        String explain = authorityBo.getExplain();
        QueryWrapper<Authority> queryWrapper = new QueryWrapper<>();
        if (StringUtils.hasText(authority)) {
            queryWrapper.like("authority", authority);
        }
        if (StringUtils.hasText(explain)) {
            queryWrapper.like("`explain`", explain);
        }

        return list(queryWrapper);
    }

    /**
     * 分页查询 权限与权限说明表
     *
     * @param authorityPageBo 权限与权限说明分页参数
     * @return 返回 查询结果
     */
    @Override
    public IPage<Authority> pageByAuthorityPageBo(AuthorityPageBo authorityPageBo) {
        QueryWrapper<Authority> queryWrapper = new QueryWrapper<>();
        Long current = authorityPageBo.getCurrent();
        Long size = authorityPageBo.getSize();
        String authority = authorityPageBo.getAuthority();
        String explain = authorityPageBo.getExplain();

        if (StringUtils.hasText(authority)) {
            queryWrapper.like("authority", authority);
        }
        if (StringUtils.hasText(explain)) {
            queryWrapper.like("`explain`", explain);
        }

        IPage<Authority> page = new Page<>(current == null ? 1 : current, size == null ? 10 : size);
        return page(page, queryWrapper);
    }

    /**
     * 根据 用户名 查询权限
     *
     * @param username 用户名
     * @return 返回 权限
     */
    @Override
    public Set<AuthorityVo> listByUsername(String username) {
        return baseMapper.listByUsername(username);
    }

}
