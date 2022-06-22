package cloud.xuxiaowei.next.system.annotation;

import cloud.xuxiaowei.next.system.validation.UsernameLogicValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 用户名 验证 注解
 * <p>
 * 用户名存在时异常（为 null 时跳过验证）
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Documented
@Constraint(validatedBy = { UsernameLogicValidation.class })
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameLogicAnnotation {

	String message() default "用户名 已被逻辑删除";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
