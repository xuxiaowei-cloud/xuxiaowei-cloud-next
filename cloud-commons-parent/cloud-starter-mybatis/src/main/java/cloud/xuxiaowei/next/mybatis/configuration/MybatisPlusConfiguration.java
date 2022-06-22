package cloud.xuxiaowei.next.mybatis.configuration;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Mybatis Plus 配置
 * <p>
 * 扫描 Mapper 接口
 * <p>
 * 分页插件
 *
 * @author 徐晓伟
 * @since 0.0.1
 */
@Configuration
@EnableTransactionManagement
@MapperScan("cloud.xuxiaowei.next.*.mapper.**")
public class MybatisPlusConfiguration {

	/**
	 * 新的分页插件、攻击 SQL 阻断解析器，防止全表更新与删除
	 * <p>
	 * 一缓和二缓遵循mybatis的规则
	 * <p>
	 * 需要设置 {@link MybatisConfiguration#setConfigurationFactory(Class)} = false
	 * 避免缓存出现问题(该属性会在旧插件移除后一同移除)
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

		// 分页拦截器
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor());

		// 攻击 SQL 阻断解析器,防止全表更新与删除
		interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

		return interceptor;
	}

}
