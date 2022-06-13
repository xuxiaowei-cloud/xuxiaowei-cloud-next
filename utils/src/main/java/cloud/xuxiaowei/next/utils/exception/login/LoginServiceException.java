package cloud.xuxiaowei.next.utils.exception.login;

import cloud.xuxiaowei.next.utils.CodeEnums;

/**
 * 登录 服务 异常
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class LoginServiceException extends LoginException {

    public LoginServiceException() {
        super(CodeEnums.A10000.code, CodeEnums.A10000.msg);
    }

    public LoginServiceException(String msg) {
        super(CodeEnums.A10000.code, msg);
    }

    public LoginServiceException(String code, String msg) {
        super(code, msg);
    }

    public LoginServiceException(String code, String msg, Throwable cause) {
        super(code, msg, cause);
    }

    public LoginServiceException(String code, String msg, String field, Throwable cause) {
        super(code, msg, field, cause);
    }

    public LoginServiceException(String code, String msg, String field, String explain, Throwable cause) {
        super(code, msg, field, explain, cause);
    }

    public LoginServiceException(Throwable cause) {
        super(CodeEnums.A10000.code, CodeEnums.A10000.code, cause);
    }

    public LoginServiceException(String msg, Throwable cause) {
        super(CodeEnums.A10000.code, msg, cause);
    }

}
