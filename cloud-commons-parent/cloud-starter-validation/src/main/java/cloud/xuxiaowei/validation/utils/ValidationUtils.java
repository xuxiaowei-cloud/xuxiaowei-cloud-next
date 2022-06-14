package cloud.xuxiaowei.validation.utils;

import cloud.xuxiaowei.next.utils.CodeEnums;
import cloud.xuxiaowei.next.utils.exception.CloudRuntimeException;
import com.google.common.base.Joiner;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 验证工具类
 *
 * @author 徐晓伟
 */
public class ValidationUtils {

    /**
     * 验证异常
     *
     * @param object 数据
     */
    public static void validate(Object object) {

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Object>> validate = validator.validate(object);

        // 验证结果异常
        if (validate.size() > 0) {
            List<String> fieldList = new LinkedList<>();
            List<String> msgList = new LinkedList<>();
            for (ConstraintViolation<Object> next : validate) {
                fieldList.add(next.getPropertyPath().toString());
                msgList.add(next.getMessage());
            }

            String field = Joiner.on(",").join(fieldList);
            String msg = Joiner.on(",").join(msgList);

            throw new CloudRuntimeException(CodeEnums.B10003.code, msg, field);
        }
    }

}
