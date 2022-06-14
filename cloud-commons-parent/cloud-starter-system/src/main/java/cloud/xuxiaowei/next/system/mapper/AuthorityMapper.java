package cloud.xuxiaowei.next.system.mapper;

import cloud.xuxiaowei.next.system.entity.Authority;
import cloud.xuxiaowei.next.system.vo.AuthorityVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 * 权限与权限说明表 Mapper 接口
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-05-17
 */
public interface AuthorityMapper extends BaseMapper<Authority> {

    /**
     * 根据 用户名 查询权限
     *
     * @param username 用户名
     * @return 返回 权限
     */
    Set<AuthorityVo> listByUsername(@Param("username") String username);

}
