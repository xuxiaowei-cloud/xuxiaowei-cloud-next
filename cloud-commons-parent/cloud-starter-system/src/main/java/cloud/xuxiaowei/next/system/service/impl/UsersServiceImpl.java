package cloud.xuxiaowei.next.system.service.impl;

import cloud.xuxiaowei.next.system.bo.*;
import cloud.xuxiaowei.next.system.entity.Authorities;
import cloud.xuxiaowei.next.system.entity.Users;
import cloud.xuxiaowei.next.system.mapper.UsersMapper;
import cloud.xuxiaowei.next.system.service.IAuthorityService;
import cloud.xuxiaowei.next.system.service.IUsersService;
import cloud.xuxiaowei.next.system.service.SessionService;
import cloud.xuxiaowei.next.system.vo.AuthorityVo;
import cloud.xuxiaowei.next.system.vo.UsersVo;
import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.SecurityUtils;
import cloud.xuxiaowei.next.utils.exception.CloudRuntimeException;
import cloud.xuxiaowei.next.validation.utils.ValidationUtils;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表。
 * 原表结构：spring-security-core-*.*.*.jar!/org/springframework/security/core/userdetails/jdbc/users.ddl
 * 原表结构：https://github.com/spring-projects/spring-security/blob/main/core/src/main/resources/org/springframework/security/core/userdetails/jdbc/users.ddl
 * GitCode
 * 镜像仓库：https://gitcode.net/mirrors/spring-projects/spring-security/blob/main/core/src/main/resources/org/springframework/security/core/userdetails/jdbc/users.ddl
 * 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-04-04
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

	private IAuthorityService authorityService;

	private SessionService sessionService;

	@Autowired
	public void setAuthorityService(IAuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	@Autowired
	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	/**
	 * 按用户名加载用户及权限（包含用户组权限）
	 * <p>
	 * 权限为空已剔除
	 * @param username 用户名
	 * @return 返回 用户信息及权限（包含用户组权限）
	 */
	@Override
	public Users loadUserByUsername(String username) {
		Users users = baseMapper.loadUserByUsername(username);
		if (users != null) {
			// 去除 authority 为空的情况
			List<Authorities> list = new ArrayList<>();
			List<Authorities> authoritiesList = users.getAuthoritiesList();
			for (Authorities authorities : authoritiesList) {
				String authority = authorities.getAuthority();
				if (StringUtils.hasText(authority)) {
					list.add(authorities);
				}
			}
			users.setAuthoritiesList(list);
		}
		return users;
	}

	/**
	 * 根据 用户名 查询用户信息、性别、区域地址及权限
	 * <p>
	 * 待加入Redis注解进行数据缓存
	 * <p>
	 * 与 {@link IUsersService#getUsersVoByUsername(String)} 可以考虑合并成一个接口
	 * @param username 用户名
	 * @return 返回 用户名 查询用户信息、性别、区域地址及权限
	 */
	@Override
	public Users getByUsername(String username) {
		return baseMapper.getByUsername(username);
	}

	/**
	 * 根据 用户名 查询用户信息
	 * <p>
	 * 条件无逻辑删除的判断
	 * @param username 用户名
	 * @return 返回 用户信息
	 */
	@Override
	public Users getLogicByUsername(String username) {
		return baseMapper.getLogicByUsername(username);
	}

	/**
	 * 根据 昵称 查询用户信息
	 * @param nickname 昵称
	 * @return 返回 用户信息
	 */
	@Override
	public Users getByNickname(String nickname) {
		QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("nickname", nickname);
		return getOne(queryWrapper);
	}

	/**
	 * 根据 昵称 查询用户信息
	 * <p>
	 * 条件无逻辑删除的判断
	 * @param nickname 昵称
	 * @return 返回 用户信息
	 */
	@Override
	public Users getLogicByNickname(String nickname) {
		return baseMapper.getLogicByNickname(nickname);
	}

	/**
	 * 根据 用户名 查询用户信息、性别、区域地址及权限
	 * @param username 用户名
	 * @return 返回 用户信息、性别、区域地址及权限
	 */
	@Override
	public UsersVo getUsersVoByUsername(String username) {
		Users users = baseMapper.getByUsername(username);
		return usersToUsersVo(users);
	}

	/**
	 * 分页查询用户
	 * @param manageUsersPageBo 管理用户分页参数
	 * @return 返回 分页查询结果
	 */
	@Override
	public IPage<UsersVo> pageByManageUsers(ManageUsersPageBo manageUsersPageBo) {
		QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
		Long current = manageUsersPageBo.getCurrent();
		Long size = manageUsersPageBo.getSize();

		Long usersId = manageUsersPageBo.getUsersId();
		String username = manageUsersPageBo.getUsername();
		String email = manageUsersPageBo.getEmail();
		Boolean emailValid = manageUsersPageBo.getEmailValid();
		String nickname = manageUsersPageBo.getNickname();

		if (usersId != null) {
			queryWrapper.eq("users_id", usersId);
		}
		if (StringUtils.hasText(username)) {
			queryWrapper.eq("username", username);
		}
		if (StringUtils.hasText(email)) {
			queryWrapper.eq("email", email);
		}
		if (emailValid != null) {
			queryWrapper.eq("email_valid", emailValid);
		}
		if (StringUtils.hasText(nickname)) {
			queryWrapper.eq("nickname", nickname);
		}

		IPage<Users> page = new Page<>(current == null ? 1 : current, size == null ? 10 : size);
		page(page, queryWrapper);

		Page<UsersVo> usersVoPage = new Page<>();
		BeanUtils.copyProperties(page, usersVoPage);

		List<UsersVo> usersVoList = new ArrayList<>();
		usersVoPage.setRecords(usersVoList);

		List<Users> records = page.getRecords();
		for (Users users : records) {
			UsersVo usersVo = new UsersVo();
			BeanUtils.copyProperties(users, usersVo);

			Set<AuthorityVo> authorityList = authorityService.listByUsername(usersVo.getUsername());
			usersVo.setAuthorityList(authorityList);

			usersVoList.add(usersVo);
		}

		return usersVoPage;
	}

	/**
	 * 根据 用户主键 查询
	 * @param usersId 用户主键
	 * @return 返回 查询结果
	 */
	@Override
	public UsersVo getUsersVoById(Long usersId) {
		Users users = baseMapper.getByUsersId(usersId);
		return usersToUsersVo(users);
	}

	private UsersVo usersToUsersVo(Users users) {
		if (users == null) {
			return null;
		}
		UsersVo usersVo = new UsersVo();
		BeanUtils.copyProperties(users, usersVo);

		Set<AuthorityVo> authorityVoSet = new HashSet<>();
		usersVo.setAuthorityList(authorityVoSet);
		for (Authorities auth : users.getAuthoritiesList()) {
			AuthorityVo authorityVo = new AuthorityVo();
			authorityVo.setAuthority(auth.getAuthority());
			authorityVo.setExplain(auth.getExplain());
			authorityVoSet.add(authorityVo);
		}
		return usersVo;
	}

	/**
	 * 保存用户
	 * @param usersSaveBo 用户
	 * @return 返回 保存结果
	 */
	@Override
	public boolean saveUsersSaveBo(UsersSaveBo usersSaveBo) {

		String passwordDecrypt = passwordDecrypt(usersSaveBo.getCode(), usersSaveBo.getPassword());

		if (!StringUtils.hasText(passwordDecrypt)) {
			throw new CloudRuntimeException("密码 不能为空");
		}

		Users users = new Users();
		BeanUtils.copyProperties(usersSaveBo, users);

		users.setPassword(passwordDecrypt);

		// 用户密码加密
		encode(users);

		return save(users);
	}

	private String passwordDecrypt(String code, String password) {
		if (password == null || Boolean.FALSE.toString().equals(password)) {
			return null;
		}
		String privateKey = sessionService.getAttr(Constant.PRIVATE_KEY + ":" + code);

		String passwordDecrypt;
		if (StringUtils.hasText(privateKey)) {
			RSA rsa = new RSA(privateKey, null);

			passwordDecrypt = rsa.decryptStr(password, KeyType.PrivateKey);
			ValidationUtils.validate(new PasswordBo(passwordDecrypt));
		}
		else {
			throw new CloudRuntimeException("未找到RSA私钥，请刷新页面后重试");
		}
		return passwordDecrypt;
	}

	/**
	 * 更新用户
	 * @param usersUpdateByIdBo 用户
	 * @return 返回 更新结果
	 */
	@Override
	public boolean updateByUsersUpdateByIdBo(UsersUpdateByIdBo usersUpdateByIdBo) {

		String passwordDecrypt = passwordDecrypt(usersUpdateByIdBo.getCode(), usersUpdateByIdBo.getPassword());

		Users users = new Users();
		BeanUtils.copyProperties(usersUpdateByIdBo, users);

		Long usersId = usersUpdateByIdBo.getUsersId();

		String email = usersUpdateByIdBo.getEmail();
		// 邮箱，唯一键：uk__users__email
		List<Users> usersEmailList = listByIdNotUsersIdAndEmail(usersId, email, null);
		if (usersEmailList.size() > 0) {
			throw new CloudRuntimeException("已存在此邮箱：" + email);
		}

		// 昵称，不能为空，唯一键：uk__users__nickname
		String nickname = usersUpdateByIdBo.getNickname();
		List<Users> usersNicknameList = listByIdNotUsersIdAndNickname(usersId, nickname, null);
		if (usersNicknameList.size() > 0) {
			throw new CloudRuntimeException("已存在此昵称：" + nickname);
		}

		users.setPassword(passwordDecrypt);

		// 用户密码加密
		encode(users);

		return updateById(users);
	}

	/**
	 * 根据当前操作人更新用户
	 * @param usersUpdateBo 用户表
	 * @return 返回 更新结果
	 */
	@Override
	public boolean updateByUsersUpdateBo(UsersUpdateBo usersUpdateBo) {
		Long usersId = SecurityUtils.getUsersId();
		Users users = new Users();
		BeanUtils.copyProperties(usersUpdateBo, users);
		users.setUsersId(usersId);
		return updateById(users);
	}

	/**
	 * 获取不是某个用户是否存在指定邮箱的用户
	 * @param usersId 用户ID
	 * @param email 邮箱
	 * @param deleted 是否逻辑删除
	 * @return 返回 用户信息
	 */
	@Override
	public List<Users> listByIdNotUsersIdAndEmail(Long usersId, String email, Boolean deleted) {
		return baseMapper.listByIdNotUsersIdAndEmail(usersId, email, deleted);
	}

	/**
	 * 获取不是某个用户是否存在指定昵称的用户
	 * @param usersId 用户ID
	 * @param nickname 昵称
	 * @param deleted 是否逻辑删除
	 * @return 返回 用户信息
	 */
	public List<Users> listByIdNotUsersIdAndNickname(Long usersId, String nickname, Boolean deleted) {
		return baseMapper.listByIdNotUsersIdAndNickname(usersId, nickname, deleted);
	}

	/**
	 * 根据 邮箱 查询用户
	 * @param email 邮箱
	 * @return 返回 查询结果
	 */
	@Override
	public Users getByEmail(String email) {
		QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("email", email);
		return getOne(queryWrapper);
	}

	/**
	 * 根据 邮箱 查询用户信息
	 * <p>
	 * 条件无逻辑删除的判断
	 * @param email 邮箱
	 * @return 返回 用户信息
	 */
	@Override
	public Users getLogicByEmail(String email) {
		return baseMapper.getLogicByEmail(email);
	}

	/**
	 * 用户密码加密
	 * @param users 用户
	 */
	private void encode(Users users) {
		// 密码加密后储存
		String password = users.getPassword();
		if (StringUtils.hasText(password)) {
			PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
			String encode = delegatingPasswordEncoder.encode(password);
			users.setPassword(encode);
		}
		else {
			users.setPassword(null);
		}
	}

}
