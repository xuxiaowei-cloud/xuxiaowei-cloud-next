package cloud.xuxiaowei.next.system.annotation;

import cloud.xuxiaowei.next.system.validation.UsersIdValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 用户主键 验证 注解
 * <p>
 * 用户主键不存在时异常（为 null 时跳过验证）
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Documented
@Constraint(validatedBy = {UsersIdValidation.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsersIdAnnotation {

    String message() default "用户主键 不合法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
