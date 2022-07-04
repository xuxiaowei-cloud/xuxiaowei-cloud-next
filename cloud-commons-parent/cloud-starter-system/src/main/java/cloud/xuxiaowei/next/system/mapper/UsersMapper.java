package cloud.xuxiaowei.next.system.mapper;

import cloud.xuxiaowei.next.system.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表。
 * 原表结构：spring-security-core-*.*.*.jar!/org/springframework/security/core/userdetails/jdbc/users.ddl
 * 原表结构：https://github.com/spring-projects/spring-security/blob/main/core/src/main/resources/org/springframework/security/core/userdetails/jdbc/users.ddl
 * GitCode
 * 镜像仓库：https://gitcode.net/mirrors/spring-projects/spring-security/blob/main/core/src/main/resources/org/springframework/security/core/userdetails/jdbc/users.ddl
 * Mapper 接口
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-04-04
 */
public interface UsersMapper extends BaseMapper<Users> {

	/**
	 * 按用户名加载用户及权限（包含用户组权限）
	 * <p>
	 * 可能存在空权限的情况，需要调用者额外处理
	 * @param username 用户名
	 * @return 返回 用户信息及权限（包含用户组权限）
	 */
	Users loadUserByUsername(@Param("username") String username);

	/**
	 * 根据 用户主键 查询用户信息及权限
	 * @param usersId 用户主键
	 * @return 返回 用户信息及权限
	 */
	Users getByUsersId(@Param("usersId") Long usersId);

	/**
	 * 根据 用户名 查询用户信息及权限
	 * @param username 用户名
	 * @return 返回 用户信息及权限
	 */
	Users getByUsername(@Param("username") String username);

	/**
	 * 根据 用户名 查询用户信息
	 * <p>
	 * 条件无逻辑删除的判断
	 * @param username 用户名
	 * @return 返回 用户信息
	 */
	Users getLogicByUsername(@Param("username") String username);

	/**
	 * 根据 昵称 查询用户信息
	 * <p>
	 * 条件无逻辑删除的判断
	 * @param nickname 昵称
	 * @return 返回 用户信息
	 */
	Users getLogicByNickname(@Param("nickname") String nickname);

	/**
	 * 根据 邮箱 查询用户信息
	 * <p>
	 * 条件无逻辑删除的判断
	 * @param email 邮箱
	 * @return 返回 用户信息
	 */
	Users getLogicByEmail(@Param("email") String email);

	/**
	 * 获取不是某个用户是否存在指定邮箱的用户
	 * @param usersId 用户ID
	 * @param email 邮箱
	 * @param deleted 是否逻辑删除
	 * @return 返回 用户信息
	 */
	List<Users> listByIdNotUsersIdAndEmail(@Param("usersId") Long usersId, @Param("email") String email,
			@Param("deleted") Boolean deleted);

	/**
	 * 获取不是某个用户是否存在指定昵称的用户
	 * @param usersId 用户ID
	 * @param nickname 昵称
	 * @param deleted 是否逻辑删除
	 * @return 返回 用户信息
	 */
	List<Users> listByIdNotUsersIdAndNickname(@Param("usersId") Long usersId, @Param("nickname") String nickname,
			@Param("deleted") Boolean deleted);

}
