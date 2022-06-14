package cloud.xuxiaowei.next.system.annotation;

import cloud.xuxiaowei.next.system.validation.NicknameExistValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 昵称 验证 注解
 * <p>
 * 昵称存在时异常（为 null 时跳过验证）
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Documented
@Constraint(validatedBy = {NicknameExistValidation.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NicknameExistAnnotation {

    String message() default "昵称 已存在";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
