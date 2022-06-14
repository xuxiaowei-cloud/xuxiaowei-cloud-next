package cloud.xuxiaowei.next.validation.utils;

import cloud.xuxiaowei.next.utils.Response;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.base.Joiner;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * 属性异常
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class FieldErrorUtils {

    /**
     * 字段转换异常统一处理
     *
     * @param error          异常
     * @param fieldErrorList 异常字段列表
     */
    public static void list(Response<?> error, List<FieldError> fieldErrorList) {

        List<String> fieldList = new ArrayList<>();
        List<String> messageList = new ArrayList<>();

        for (FieldError fieldError : fieldErrorList) {
            String field = fieldError.getField();
            fieldList.add(field);

            String defaultMessage = fieldError.getDefaultMessage();
            messageList.add(defaultMessage);
        }

        error.setField(Joiner.on(",").join(fieldList));
        error.setMsg(Joiner.on(",").join(messageList));
    }

    /**
     * 异常字段转换
     *
     * @param referenceList 异常字段
     */
    public static String reference(List<JsonMappingException.Reference> referenceList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (JsonMappingException.Reference reference : referenceList) {
            String fieldName = reference.getFieldName();
            if (fieldName == null) {
                String description = reference.getDescription();
                String name = ArrayList.class.getName();
                if (description.contains(name)) {
                    String replace = description.replace(name, "");
                    stringBuilder.append(replace);
                }
            } else {
                int length = stringBuilder.length();
                if (length == 0) {
                    stringBuilder.append(fieldName);
                } else {
                    stringBuilder.append(".").append(fieldName);
                }
            }
        }
        return stringBuilder.toString();
    }

}
