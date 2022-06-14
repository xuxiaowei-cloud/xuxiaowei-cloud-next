package cloud.xuxiaowei.next.utils.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常 工具类
 *
 * @author xuxiaowei
 * @see <a href="https://mvnrepository.com/artifact/org.apache.commons/commons-lang3">commons-lang3</a>
 * @since 0.0.1
 */
public class ExceptionUtils {

    /**
     * 获取异常信息
     * <p>
     * 该方法来自 org.apache.commons:commons-lang3，没有必要为了一个方法引入一个包
     *
     * @param throwable 异常
     * @return 返回 异常信息
     */
    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

}
