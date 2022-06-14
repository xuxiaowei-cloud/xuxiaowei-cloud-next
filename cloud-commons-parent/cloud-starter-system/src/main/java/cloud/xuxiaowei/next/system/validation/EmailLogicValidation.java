package cloud.xuxiaowei.next.system.validation;

import cloud.xuxiaowei.next.system.annotation.EmailLogicAnnotation;
import cloud.xuxiaowei.next.system.service.IUsersService;
import cloud.xuxiaowei.next.system.entity.Users;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForCharSequence;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 邮箱 验证
 * <p>
 * 邮箱存在时异常（为 null 时跳过验证）
 *
 * @author xuxiaowei
 * @see SizeValidatorForCharSequence
 * @since 0.0.1
 */
@Slf4j
public class EmailLogicValidation implements ConstraintValidator<EmailLogicAnnotation, String> {

    private IUsersService usersService;

    @Autowired
    public void setUsersService(IUsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public void initialize(EmailLogicAnnotation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null) {
            Users users = usersService.getLogicByEmail(value);
            if (users == null) {
                return true;
            } else {
                Boolean deleted = users.getDeleted();
                return deleted != null && !deleted;
            }
        }
        return true;
    }

}
