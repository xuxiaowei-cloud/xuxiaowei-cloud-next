package cloud.xuxiaowei.next.system.aspect;

import cloud.xuxiaowei.next.system.annotation.ControllerAnnotation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * {@link Controller}、{@link RestController} 切面日志
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Slf4j
@Aspect
@Component
public class ControllerLogAspect {

	/**
	 * 切点
	 */
	@Pointcut("execution(* cloud.xuxiaowei.next.*.controller.*.*(..)) ")
	public void pointcut() {

	}

	/**
	 * 环绕通知
	 * @param joinPoint 切面方法信息
	 * @return 执行结果
	 * @throws Throwable 执行异常
	 */
	@Around("pointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

		// 开始时间戳
		long startTime = System.currentTimeMillis();

		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		String requestUri;
		if (requestAttributes == null) {
			requestUri = null;
		}
		else {
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;

			// 获取 Http 请求
			HttpServletRequest request = servletRequestAttributes.getRequest();
			// 获取 Http 响应
			HttpServletResponse response = servletRequestAttributes.getResponse();

			// URI
			requestUri = request.getRequestURI();
		}

		ControllerAnnotation controllerAnnotation = ControllerAnnotation.Annotation.get(joinPoint);
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String method = signature.getMethod().toString();
		String description = controllerAnnotation == null ? method : controllerAnnotation.description();

		log.info("前置通知：{}，URI：{}", description, requestUri);

		try {
			// 执行 Controller
			Object proceed = joinPoint.proceed();
			long endTime = System.currentTimeMillis();
			long takeUpTime = endTime - startTime;

			log.info("后置通知：{}，耗时：{} ms", description, takeUpTime);
			return proceed;
		}
		catch (Exception e) {
			long endTime = System.currentTimeMillis();
			long takeUpTime = endTime - startTime;
			log.error("异常通知：{}，耗时：{} ms，异常：{}", description, takeUpTime, e);

			// 抛出原始异常
			throw e;
		}
	}

}
