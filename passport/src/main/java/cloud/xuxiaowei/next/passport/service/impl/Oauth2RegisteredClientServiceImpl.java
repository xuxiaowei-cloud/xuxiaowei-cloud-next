package cloud.xuxiaowei.next.passport.service.impl;

import cloud.xuxiaowei.next.passport.bo.ClientSecretBo;
import cloud.xuxiaowei.next.passport.bo.Oauth2RegisteredClientPageBo;
import cloud.xuxiaowei.next.passport.bo.Oauth2RegisteredClientSaveBo;
import cloud.xuxiaowei.next.passport.bo.Oauth2RegisteredClientUpdateBo;
import cloud.xuxiaowei.next.passport.entity.Oauth2RegisteredClient;
import cloud.xuxiaowei.next.passport.mapper.Oauth2RegisteredClientMapper;
import cloud.xuxiaowei.next.passport.service.IOauth2RegisteredClientService;
import cloud.xuxiaowei.next.passport.vo.Oauth2RegisteredClientVo;
import cloud.xuxiaowei.next.system.service.SessionService;
import cloud.xuxiaowei.next.utils.Constant;
import cloud.xuxiaowei.next.utils.exception.CloudRuntimeException;
import cloud.xuxiaowei.next.validation.utils.ValidationUtils;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.OAuth2TokenFormat;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cloud.xuxiaowei.next.passport.controller.Oauth2RegisteredClientController.ALGORITHM_SPLIT;

/**
 * <p>
 * 客户表。
 * 原表结构：oauth2-authorization-server-*.*.*.jar!/org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql
 * 原表结构：https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql
 * GitCode
 * 镜像仓库：https://gitcode.net/mirrors/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql
 * 服务实现类
 * </p>
 *
 * @author xuxiaowei
 * @since 2022-06-18
 */
@Service
public class Oauth2RegisteredClientServiceImpl extends ServiceImpl<Oauth2RegisteredClientMapper, Oauth2RegisteredClient>
		implements IOauth2RegisteredClientService {

	private SessionService sessionService;

	@Autowired
	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	/**
	 * 分页查询
	 * @param oauth2RegisteredClientPageBo 客户表 分页参数
	 * @return 返回 分页结果
	 */
	@Override
	public IPage<Oauth2RegisteredClientVo> pageByOauth2RegisteredClientPageBo(
			Oauth2RegisteredClientPageBo oauth2RegisteredClientPageBo) {

		QueryWrapper<Oauth2RegisteredClient> queryWrapper = new QueryWrapper<>();
		Long current = oauth2RegisteredClientPageBo.getCurrent();
		Long size = oauth2RegisteredClientPageBo.getSize();

		String id = oauth2RegisteredClientPageBo.getId();
		String clientId = oauth2RegisteredClientPageBo.getClientId();
		String clientName = oauth2RegisteredClientPageBo.getClientName();

		if (StringUtils.hasText(id)) {
			queryWrapper.eq("id", id);
		}
		if (StringUtils.hasText(clientId)) {
			queryWrapper.eq("client_id", clientId);
		}
		if (StringUtils.hasText(clientName)) {
			queryWrapper.eq("client_name", clientName);
		}

		IPage<Oauth2RegisteredClient> page = new Page<>(current == null ? 1 : current, size == null ? 10 : size);
		page(page, queryWrapper);

		Page<Oauth2RegisteredClientVo> oauth2RegisteredClientVoPage = new Page<>();
		BeanUtils.copyProperties(page, oauth2RegisteredClientVoPage);

		List<Oauth2RegisteredClientVo> oauth2RegisteredClientVoList = new ArrayList<>();
		oauth2RegisteredClientVoPage.setRecords(oauth2RegisteredClientVoList);

		List<Oauth2RegisteredClient> records = page.getRecords();
		for (Oauth2RegisteredClient oauth2RegisteredClient : records) {
			Oauth2RegisteredClientVo oauth2RegisteredClientVo = new Oauth2RegisteredClientVo();
			BeanUtils.copyProperties(oauth2RegisteredClient, oauth2RegisteredClientVo);

			oauth2RegisteredClientVoList.add(oauth2RegisteredClientVo);
		}

		return oauth2RegisteredClientVoPage;
	}

	/**
	 * 根据 主键 查询
	 * @param id 主键
	 * @return 返回 查询结果
	 */
	@Override
	public Oauth2RegisteredClientVo getVoById(String id) {
		Oauth2RegisteredClient oauth2RegisteredClient = getById(id);
		if (oauth2RegisteredClient == null) {
			return null;
		}
		Oauth2RegisteredClientVo oauth2RegisteredClientVo = new Oauth2RegisteredClientVo();
		BeanUtils.copyProperties(oauth2RegisteredClient, oauth2RegisteredClientVo);
		return oauth2RegisteredClientVo;
	}

	/**
	 * 保存 客户
	 * @param oauth2RegisteredClientSaveBo 客户表 保存参数
	 * @return 返回 保存结果
	 */
	@SneakyThrows
	@Override
	public boolean saveOauth2RegisteredClientSaveBo(Oauth2RegisteredClientSaveBo oauth2RegisteredClientSaveBo) {
		String clientSecretDecrypt = clientSecretDecrypt(oauth2RegisteredClientSaveBo.getCode(),
				oauth2RegisteredClientSaveBo.getClientSecret());

		if (!StringUtils.hasText(clientSecretDecrypt)) {
			throw new CloudRuntimeException("客户凭证 不能为空");
		}

		Oauth2RegisteredClient oauthClientDetails = new Oauth2RegisteredClient();
		BeanUtils.copyProperties(oauth2RegisteredClientSaveBo, oauthClientDetails);

		Boolean requireProofKey = oauth2RegisteredClientSaveBo.getRequireProofKey();
		Boolean requireAuthorizationConsent = oauth2RegisteredClientSaveBo.getRequireAuthorizationConsent();
		String tokenSigningAlgorithm = oauth2RegisteredClientSaveBo.getTokenSigningAlgorithm();
		String jwkSetUrl = oauth2RegisteredClientSaveBo.getJwkSetUrl();
		String clientSettings = clientSettings(jwkSetUrl, tokenSigningAlgorithm, requireProofKey,
				requireAuthorizationConsent);
		oauthClientDetails.setClientSettings(clientSettings);

		Long accessTokenTimeToLive = oauth2RegisteredClientSaveBo.getAccessTokenTimeToLive();
		Long refreshTokenTimeToLive = oauth2RegisteredClientSaveBo.getRefreshTokenTimeToLive();
		String tokenSignatureAlgorithm = oauth2RegisteredClientSaveBo.getTokenSignatureAlgorithm();
		Boolean reuseRefreshTokens = oauth2RegisteredClientSaveBo.getReuseRefreshTokens();
		String accessTokenFormat = oauth2RegisteredClientSaveBo.getAccessTokenFormat();

		String tokenSettings = tokenSettings(accessTokenFormat, reuseRefreshTokens, tokenSignatureAlgorithm,
				accessTokenTimeToLive, refreshTokenTimeToLive);
		oauthClientDetails.setTokenSettings(tokenSettings);

		oauthClientDetails.setClientSecret(clientSecretDecrypt);

		// 客户凭证加密
		encode(oauthClientDetails);

		return save(oauthClientDetails);
	}

	private ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		ClassLoader classLoader = JdbcRegisteredClientRepository.class.getClassLoader();
		List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
		objectMapper.registerModules(securityModules);
		objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
		return objectMapper;
	}

	private String clientSettings(String jwkSetUrl, String tokenSigningAlgorithm, boolean requireProofKey,
			boolean requireAuthorizationConsent) throws JsonProcessingException {
		ClientSettings.Builder builder = ClientSettings.builder();
		Map<String, Object> settings = client(jwkSetUrl, tokenSigningAlgorithm, requireProofKey,
				requireAuthorizationConsent, builder);
		return objectMapper().writeValueAsString(settings);
	}

	private Map<String, Object> client(String jwkSetUrl, String tokenSigningAlgorithm, boolean requireProofKey,
			boolean requireAuthorizationConsent, ClientSettings.Builder builder) {
		builder.jwkSetUrl(jwkSetUrl);
		builder.requireAuthorizationConsent(requireAuthorizationConsent);
		builder.requireProofKey(requireProofKey);
		if (StringUtils.hasText(tokenSigningAlgorithm)) {
			List<String> strings = Splitter.on(ALGORITHM_SPLIT).splitToList(tokenSigningAlgorithm);
			String algorithmClasses = strings.get(0);
			String algorithmName = strings.get(1);
			if (MacAlgorithm.class.getName().equals(algorithmClasses)) {
				builder.tokenEndpointAuthenticationSigningAlgorithm(MacAlgorithm.from(algorithmName));
			}
			else if (SignatureAlgorithm.class.getName().equals(algorithmClasses)) {
				builder.tokenEndpointAuthenticationSigningAlgorithm(SignatureAlgorithm.from(algorithmName));
			}
			else {
				throw new CloudRuntimeException(String.format("非法令牌端点认证签名算法：%s", tokenSigningAlgorithm));
			}
		}
		ClientSettings build = builder.build();
		return build.getSettings();
	}

	private String clientSettings(String jwkSetUrl, String tokenSigningAlgorithm, boolean requireProofKey,
			boolean requireAuthorizationConsent, String clientSettings) throws JsonProcessingException {
		ObjectMapper objectMapper = objectMapper();
		Map<String, Object> map = objectMapper.readValue(clientSettings, new TypeReference<>() {
		});
		ClientSettings.Builder builder = ClientSettings.withSettings(map);
		Map<String, Object> settings = client(jwkSetUrl, tokenSigningAlgorithm, requireProofKey,
				requireAuthorizationConsent, builder);
		return objectMapper.writeValueAsString(settings);
	}

	private String tokenSettings(String accessTokenFormat, Boolean reuseRefreshTokens, String tokenSignatureAlgorithm,
			long accessTokenTimeToLive, long refreshTokenTimeToLive) throws JsonProcessingException {
		TokenSettings.Builder builder = TokenSettings.builder();
		Map<String, Object> settings = setting(accessTokenFormat, reuseRefreshTokens, tokenSignatureAlgorithm,
				accessTokenTimeToLive, refreshTokenTimeToLive, builder);
		return objectMapper().writeValueAsString(settings);
	}

	private String tokenSettings(String accessTokenFormat, Boolean reuseRefreshTokens, String tokenSignatureAlgorithm,
			long accessTokenTimeToLive, long refreshTokenTimeToLive, String tokenSettings)
			throws JsonProcessingException {
		ObjectMapper objectMapper = objectMapper();
		Map<String, Object> map = objectMapper.readValue(tokenSettings, new TypeReference<>() {
		});
		TokenSettings.Builder builder = TokenSettings.withSettings(map);
		Map<String, Object> setting = setting(accessTokenFormat, reuseRefreshTokens, tokenSignatureAlgorithm,
				accessTokenTimeToLive, refreshTokenTimeToLive, builder);
		return objectMapper.writeValueAsString(setting);
	}

	private Map<String, Object> setting(String accessTokenFormat, Boolean reuseRefreshTokens,
			String tokenSignatureAlgorithm, long accessTokenTimeToLive, long refreshTokenTimeToLive,
			TokenSettings.Builder builder) {
		builder.accessTokenTimeToLive(Duration.ofSeconds(accessTokenTimeToLive));
		builder.refreshTokenTimeToLive(Duration.ofSeconds(refreshTokenTimeToLive));
		if (reuseRefreshTokens != null) {
			builder.reuseRefreshTokens(reuseRefreshTokens);
		}
		if (accessTokenFormat != null) {
			builder.accessTokenFormat(new OAuth2TokenFormat(accessTokenFormat));
		}

		if (StringUtils.hasText(tokenSignatureAlgorithm)) {
			List<String> strings = Splitter.on(ALGORITHM_SPLIT).splitToList(tokenSignatureAlgorithm);
			String algorithmClasses = strings.get(0);
			String algorithmName = strings.get(1);
			if (SignatureAlgorithm.class.getName().equals(algorithmClasses)) {
				builder.idTokenSignatureAlgorithm(SignatureAlgorithm.from(algorithmName));
			}
			else {
				throw new CloudRuntimeException(String.format("非法id 令牌签名算法：%s", tokenSignatureAlgorithm));
			}
		}

		TokenSettings build = builder.build();
		return build.getSettings();
	}

	/**
	 * 根据主键更新客户表
	 * @param oauth2RegisteredClientUpdateBo 客户表 更新参数
	 * @return 返回 更新结果
	 */
	@SneakyThrows
	@Override
	public boolean updateByOauth2RegisteredClientUpdateBo(
			Oauth2RegisteredClientUpdateBo oauth2RegisteredClientUpdateBo) {

		String id = oauth2RegisteredClientUpdateBo.getId();
		Oauth2RegisteredClient byId = getById(id);
		if (byId == null) {
			throw new CloudRuntimeException("修改失败，该记录不存在");
		}

		String clientSecretDecrypt = clientSecretDecrypt(oauth2RegisteredClientUpdateBo.getCode(),
				oauth2RegisteredClientUpdateBo.getClientSecret());

		Oauth2RegisteredClient oauthClientDetails = new Oauth2RegisteredClient();
		BeanUtils.copyProperties(oauth2RegisteredClientUpdateBo, oauthClientDetails);

		Boolean requireProofKey = oauth2RegisteredClientUpdateBo.getRequireProofKey();
		Boolean requireAuthorizationConsent = oauth2RegisteredClientUpdateBo.getRequireAuthorizationConsent();
		String tokenSigningAlgorithm = oauth2RegisteredClientUpdateBo.getTokenSigningAlgorithm();
		String jwkSetUrl = oauth2RegisteredClientUpdateBo.getJwkSetUrl();
		String clientSettings = clientSettings(jwkSetUrl, tokenSigningAlgorithm, requireProofKey,
				requireAuthorizationConsent, byId.getClientSettings());
		oauthClientDetails.setClientSettings(clientSettings);

		Long accessTokenTimeToLive = oauth2RegisteredClientUpdateBo.getAccessTokenTimeToLive();
		Long refreshTokenTimeToLive = oauth2RegisteredClientUpdateBo.getRefreshTokenTimeToLive();
		String tokenSignatureAlgorithm = oauth2RegisteredClientUpdateBo.getTokenSignatureAlgorithm();
		Boolean reuseRefreshTokens = oauth2RegisteredClientUpdateBo.getReuseRefreshTokens();
		String accessTokenFormat = oauth2RegisteredClientUpdateBo.getAccessTokenFormat();
		String tokenSettings = tokenSettings(accessTokenFormat, reuseRefreshTokens, tokenSignatureAlgorithm,
				accessTokenTimeToLive, refreshTokenTimeToLive, byId.getTokenSettings());
		oauthClientDetails.setTokenSettings(tokenSettings);

		oauthClientDetails.setClientSecret(clientSecretDecrypt);

		// 客户凭证加密
		encode(oauthClientDetails);

		return updateById(oauthClientDetails);
	}

	private String clientSecretDecrypt(String code, String clientSecret) {
		String privateKey = sessionService.getAttr(Constant.PRIVATE_KEY + ":" + code);

		String clientSecretDecrypt;
		if (StringUtils.hasText(privateKey)) {
			RSA rsa = new RSA(privateKey, null);

			if (Boolean.FALSE.toString().equals(clientSecret)) {
				return null;
			}

			clientSecretDecrypt = rsa.decryptStr(clientSecret, KeyType.PrivateKey);
			ValidationUtils.validate(new ClientSecretBo(clientSecretDecrypt));
		}
		else {
			throw new CloudRuntimeException("未找到RSA私钥，请刷新页面后重试");
		}
		return clientSecretDecrypt;
	}

	/**
	 * 客户凭证加密
	 * @param oauthClientDetails 客户
	 */
	private void encode(Oauth2RegisteredClient oauthClientDetails) {
		// 凭证加密后储存
		String clientSecret = oauthClientDetails.getClientSecret();
		if (StringUtils.hasText(clientSecret)) {
			PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
			String encode = delegatingPasswordEncoder.encode(clientSecret);
			oauthClientDetails.setClientSecret(encode);
		}
		else {
			oauthClientDetails.setClientSecret(null);
		}
	}

}
