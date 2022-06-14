package cloud.xuxiaowei.next.system.validation;

import cloud.xuxiaowei.next.system.annotation.UsernameExistAnnotation;
import cloud.xuxiaowei.next.system.entity.Users;
import cloud.xuxiaowei.next.system.service.IUsersService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForCharSequence;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户名 验证
 * <p>
 * 用户名存在时异常（为 null 时跳过验证）
 *
 * @author xuxiaowei
 * @see SizeValidatorForCharSequence
 * @since 0.0.1
 */
@Slf4j
public class UsernameExistValidation implements ConstraintValidator<UsernameExistAnnotation, String> {

    private IUsersService usersService;

    @Autowired
    public void setUsersService(IUsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public void initialize(UsernameExistAnnotation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null) {
            Users users = usersService.getByUsername(value);
            return users == null;
        }
        return true;
    }

}
