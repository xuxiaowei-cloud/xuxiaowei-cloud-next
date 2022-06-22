package cloud.xuxiaowei.next.passport;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

/**
 * 用户信息 测试类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@SpringBootTest
public class UserDetailsServiceTests {

	private DataSource dataSource;

	private UserDetailsService userDetailsService;

	private JdbcUserDetailsManager jdbcUserDetailsManager;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.userDetailsService = new JdbcUserDetailsManager(dataSource);
		this.jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
	}

	/**
	 * 创建用户
	 */
	@Test
	void createUser() {
		UserDetails userDetails = User.withDefaultPasswordEncoder().username("user").password("password")
				.authorities("user_info").build();
		jdbcUserDetailsManager.createUser(userDetails);
	}

}
