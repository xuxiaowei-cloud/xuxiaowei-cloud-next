package cloud.xuxiaowei.next.example.cxfclient;

import cloud.xuxiaowei.next.example.cxfclient.bo.UserBo;
import cloud.xuxiaowei.next.example.cxfclient.service.UserServiceClient;
import cloud.xuxiaowei.next.example.cxfclient.utils.CxfClientUtils;
import cloud.xuxiaowei.next.example.cxfclient.vo.ResponseUserVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户 WebService 接口 客户端 测试类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
class UserServiceClientTests {

	@Test
	void getById() {

		Map<String, List<String>> headers = new HashMap<>();

		headers.put("authorization", Collections.singletonList(
				"Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NTA3MzE2MzksInVzZXJfbmFtZSI6Inh1eGlhb3dlaSIsImF1dGhvcml0aWVzIjpbImF1ZGl0X3JlZnJlc2hUb2tlbl9yZWFkIiwidXNlcl9pbmZvIiwidXNlcl9vYXV0aDJfdXNlckF1dGhlbnRpY2F0aW9uIiwidXNlcl9kZXRhaWxzIiwiYXVkaXRfY29kZV9kZWxldGUiLCJhdWRpdF9hY2Nlc3NUb2tlbl9yZWFkIiwidXNlcl9vYXV0aDJfb2F1dGgyUmVxdWVzdCIsImF1ZGl0X2NvZGVfcmVhZCIsInVzZXJfYXV0aG9yaXRpZXMiLCJhdWRpdF9hY2Nlc3NUb2tlbl9kZWxldGUiLCJhdWRpdF9yZWZyZXNoVG9rZW5fZGVsZXRlIl0sImp0aSI6Il9Nei1SRUMtTEt0MEpUYlJPNWdDTVgwS3FFQSIsImNsaWVudF9pZCI6Inh1eGlhb3dlaV9jbGllbnRfaWQiLCJzY29wZSI6WyJzbnNhcGlfYmFzZSJdfQ.swWEzDQOV4-NeEoPQJFWLIORoQFB8gXzCwX3ZKR-Oo3umwJ8cyRWwmxiklMjaeqYs0rGXRo6mLxry2ctmOeZg6tGa6fR1Q_x8hGMZVLKlTmFFgYKX_mEVNx1iO0M2O1FLvWGDheV_ZTBeXNlHDU0LRMihVrj9O-xjpis0kioMC7oYjt7tom6yyWn2HyfcuQg_EFA7i-hbWZvIXItdU9Boy3c1UML45H_K4vpAkq-vK3hnUJJwvFVh3wv4_X-fe1i2o4po11eJwkzBUC9aBeH4ALD-r4XWjKCymQbjkSAET2fiC2uk8MGoG2PQf9QRBcrh-ojQtjn1WnMnnYMrAu24A"));

		UserServiceClient userServiceClient = CxfClientUtils.create("http://localhost:1901/cxf/userService?wsdl",
				UserServiceClient.class, headers);

		UserBo userBo = new UserBo();
		userBo.setId("1111111");
		ResponseUserVo responseUserVo = userServiceClient.getById(userBo);

		log.info(String.valueOf(responseUserVo));
	}

}
