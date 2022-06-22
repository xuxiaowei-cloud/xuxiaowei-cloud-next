package cloud.xuxiaowei.next.system.service.impl;

import cloud.xuxiaowei.next.system.entity.WxMaUsers;
import cloud.xuxiaowei.next.system.mapper.WxMaUsersMapper;
import cloud.xuxiaowei.next.system.service.IWxMaUsersService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 微信小程序用户 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-05-15
 */
@Service
public class WxMaUsersServiceImpl extends ServiceImpl<WxMaUsersMapper, WxMaUsers> implements IWxMaUsersService {

	/**
	 * 保存 openid
	 * @param appid 小程序标识
	 * @param openid 用户标识（针对于某个小程序）
	 * @param unionid 用户标识（针对于同一开放平台）
	 */
	@Override
	public boolean saveOpenid(String appid, String openid, String unionid) {
		WxMaUsers getOne = getByAppidAndOpenid(appid, openid);
		if (getOne == null) {
			WxMaUsers wxMaUsers = new WxMaUsers();
			wxMaUsers.setAppid(appid);
			wxMaUsers.setOpenid(openid);
			wxMaUsers.setUnionid(unionid);
			return save(wxMaUsers);
		}

		return false;
	}

	/**
	 * 根据 appid、openid 查询微信小程序用户
	 * @param appid 小程序标识
	 * @param openid 用户标识（针对于某个小程序）
	 * @return 返回 微信小程序用户
	 */
	public WxMaUsers getByAppidAndOpenid(String appid, String openid) {
		QueryWrapper<WxMaUsers> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("appid", appid);
		queryWrapper.eq("openid", openid);
		return getOne(queryWrapper);
	}

}
