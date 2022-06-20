package cloud.xuxiaowei.next.passport.bo;

import cloud.xuxiaowei.next.system.annotation.LowerCaseAnnotation;
import cloud.xuxiaowei.next.system.annotation.NumberAnnotation;
import cloud.xuxiaowei.next.system.annotation.SymbolAnnotation;
import cloud.xuxiaowei.next.system.annotation.UpperCaseAnnotation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * 客户凭证验证
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@AllArgsConstructor
public class ClientSecretBo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NumberAnnotation(message = "客户凭证必须包含数字")
    @LowerCaseAnnotation(message = "客户凭证必须包含小写字母")
    @UpperCaseAnnotation(message = "客户凭证必须包含大写字母")
    @SymbolAnnotation(message = "客户凭证必须包含特殊符号")
    @Length(min = 6, max = 16, message = "客户凭证 长度限制：6-16")
    private String clientSecret;

}
