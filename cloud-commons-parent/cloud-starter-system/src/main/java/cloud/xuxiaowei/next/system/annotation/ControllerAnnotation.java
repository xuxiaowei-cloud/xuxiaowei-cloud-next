package cloud.xuxiaowei.next.system.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;
import java.lang.reflect.Method;

/**
 * {@link Controller}、{@link RestController} 说明
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerAnnotation {

    /**
     * {@link Controller} 说明
     *
     * @return {@link Controller} 说明
     */
    String description();

    /**
     * 注解
     */
    class Annotation {

        /**
         * 获取 {@link Controller} 注解 说明
         *
         * @param joinPoint 接入点
         * @return 返回 获取的 {@link Controller} 注解 说明
         */
        public static ControllerAnnotation get(JoinPoint joinPoint) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            return method.getAnnotation(ControllerAnnotation.class);
        }

    }

}
