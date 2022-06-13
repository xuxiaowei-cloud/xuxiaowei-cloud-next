package cloud.xuxiaowei.next.utils.exception.login;

import cloud.xuxiaowei.next.utils.CodeEnums;

/**
 * 登录参数验证 异常父类
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class LoginParamValidException extends LoginException {

    public LoginParamValidException() {
        super(CodeEnums.A20001.code, CodeEnums.A20001.msg);
    }

    public LoginParamValidException(String msg) {
        super(CodeEnums.A20001.code, msg);
    }

    public LoginParamValidException(String code, String msg) {
        super(code, msg);
    }

    public LoginParamValidException(String code, String msg, Throwable cause) {
        super(code, msg, cause);
    }

    public LoginParamValidException(String code, String msg, String field, Throwable cause) {
        super(code, msg, field, cause);
    }

    public LoginParamValidException(String code, String msg, String field, String explain, Throwable cause) {
        super(code, msg, field, explain, cause);
    }

    public LoginParamValidException(Throwable cause) {
        super(CodeEnums.A20001.msg, cause);
        this.code = CodeEnums.A20001.code;
        this.msg = CodeEnums.A20001.msg;
    }

    public LoginParamValidException(String msg, Throwable cause) {
        super(msg, cause);
        this.code = CodeEnums.A20001.code;
        this.msg = msg;
    }

}
