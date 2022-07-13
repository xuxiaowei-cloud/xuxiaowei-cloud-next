package cloud.xuxiaowei.next.canal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 程序执行入口
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@EnableScheduling
@SpringBootApplication
public class CanalApplication {

	public static void main(String[] args) {
		SpringApplication.run(CanalApplication.class, args);
	}

}
