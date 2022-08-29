package cloud.xuxiaowei.next.system.annotation;

import cloud.xuxiaowei.next.utils.Encrypt;

import java.lang.annotation.*;

/**
 * AES 加密注解
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Documented
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptAnnotation {

	/**
	 * 加密方式（版本）
	 */
	Encrypt.AesVersion value();

	/**
	 * 客户 AES 加密注解
	 */
	ClientId[] client() default {};

	/**
	 * 客户 AES 加密注解
	 *
	 * @author xuxiaowei
	 * @since 0.0.1
	 */
	@interface ClientId {

		/**
		 * 客户ID
		 */
		String cloudId();

		/**
		 * 加密方式（版本）
		 */
		Encrypt.AesVersion value();

	}

}
