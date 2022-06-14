package cloud.xuxiaowei.next.utils.exception;

import cloud.xuxiaowei.next.utils.CodeEnums;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

/**
 * 微服务 运行时异常父类
 * <p>
 * 目前仅提供给断言使用
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CloudRuntimeException extends RuntimeException {

    /**
     * 错误码
     */
    @Setter(AccessLevel.NONE)
    protected String code;

    /**
     * 中文描述
     */
    @Setter(AccessLevel.NONE)
    protected String msg;

    /**
     * 错误字段
     * <p>
     * 存在多个时，使用英文逗号隔开
     */
    @Setter(AccessLevel.NONE)
    protected String field;

    /**
     * 说明
     */
    @Setter(AccessLevel.NONE)
    protected String explain;

    public CloudRuntimeException() {
        super(CodeEnums.ERROR.msg);
        this.code = CodeEnums.ERROR.code;
        this.msg = CodeEnums.ERROR.msg;
    }

    public CloudRuntimeException(String msg) {
        super(msg);
        this.code = CodeEnums.ERROR.code;
        this.msg = msg;
    }

    public CloudRuntimeException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public CloudRuntimeException(String code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
    }

    public CloudRuntimeException(String code, String msg, String field) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.field = field;
    }

    public CloudRuntimeException(String code, String msg, String field, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
        this.field = field;
    }

    public CloudRuntimeException(String code, String msg, String field, String explain, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
        this.field = field;
        this.explain = explain;
    }

    public CloudRuntimeException(Throwable cause) {
        super(CodeEnums.ERROR.msg, cause);
        this.code = CodeEnums.ERROR.code;
        this.msg = CodeEnums.ERROR.msg;
    }

    public CloudRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
        this.code = CodeEnums.ERROR.code;
        this.msg = msg;
    }

}
