package cloud.xuxiaowei.next.passport.service.impl;

import cloud.xuxiaowei.next.passport.bo.Oauth2AuthorizationPageBo;
import cloud.xuxiaowei.next.passport.entity.Oauth2Authorization;
import cloud.xuxiaowei.next.passport.mapper.Oauth2AuthorizationMapper;
import cloud.xuxiaowei.next.passport.service.IOauth2AuthorizationService;
import cloud.xuxiaowei.next.passport.vo.Oauth2AuthorizationVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 授权表。
 * 原表结构：oauth2-authorization-server-*.*.*.jar!/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql
 * 原表结构：https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql
 * GitCode
 * 镜像仓库：https://gitcode.net/mirrors/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql
 * 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-06-18
 */
@Service
public class Oauth2AuthorizationServiceImpl extends ServiceImpl<Oauth2AuthorizationMapper, Oauth2Authorization>
		implements IOauth2AuthorizationService {

	/**
	 * 分页查询
	 * @param oauth2AuthorizationPageBo 授权表 分页参数
	 * @return 返回 分页结果
	 */
	@Override
	public IPage<Oauth2AuthorizationVo> pageByOauth2AuthorizationPageBo(
			Oauth2AuthorizationPageBo oauth2AuthorizationPageBo) {
		QueryWrapper<Oauth2Authorization> queryWrapper = new QueryWrapper<>();
		Long current = oauth2AuthorizationPageBo.getCurrent();
		Long size = oauth2AuthorizationPageBo.getSize();

		String registeredClientId = oauth2AuthorizationPageBo.getRegisteredClientId();
		String principalName = oauth2AuthorizationPageBo.getPrincipalName();

		if (StringUtils.hasText(registeredClientId)) {
			queryWrapper.eq("registered_client_id", registeredClientId);
		}
		if (StringUtils.hasText(principalName)) {
			queryWrapper.eq("principal_name", principalName);
		}

		IPage<Oauth2Authorization> page = new Page<>(current == null ? 1 : current, size == null ? 10 : size);
		page(page, queryWrapper);

		Page<Oauth2AuthorizationVo> oauth2AuthorizationVoPage = new Page<>();
		BeanUtils.copyProperties(page, oauth2AuthorizationVoPage);

		List<Oauth2AuthorizationVo> oauth2AuthorizationVoList = new ArrayList<>();
		oauth2AuthorizationVoPage.setRecords(oauth2AuthorizationVoList);

		List<Oauth2Authorization> records = page.getRecords();
		for (Oauth2Authorization oauth2Authorization : records) {
			Oauth2AuthorizationVo oauth2AuthorizationVo = new Oauth2AuthorizationVo();
			BeanUtils.copyProperties(oauth2Authorization, oauth2AuthorizationVo);

			oauth2AuthorizationVoList.add(oauth2AuthorizationVo);
		}

		return oauth2AuthorizationVoPage;
	}

	/**
	 * 根据 授权 Token 删除 授权表中的数据
	 * @param accessToken 授权 Token
	 * @return 返回 删除结果
	 */
	@Override
	public boolean removeByAccessTokenValue(String accessToken) {
		QueryWrapper<Oauth2Authorization> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("access_token_value", accessToken);
		return remove(queryWrapper);
	}

}
