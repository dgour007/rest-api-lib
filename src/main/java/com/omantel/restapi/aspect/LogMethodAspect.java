/**
 * 
 */
package com.omantel.restapi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;


/**
 * @author Dhiraj Gour
 * @date 20 March 2018
 *
 */
@Aspect
@Component
@Slf4j
public class LogMethodAspect {

	@Pointcut(
        "execution(* om.omantel..*(..)) "
        + "&& @annotation(com.omantel.restapi.annotation.LogMethodParams)"
    )
	public void paramsMethod() {}
	
	@Pointcut(
        "execution(* om.omantel..*(..)) "
        + "&& @annotation(com.omantel.restapi.annotation.LogMethodPerformance)"
    )
	public void performanceMethod() {}
	
	@Pointcut(
        "execution(* om.omantel.*.controller..*(..)) "
        + "&& @annotation(com.omantel.restapi.annotation.LogRequest)"
    )
	public void controllerRequest() {}
    
    @Before("paramsMethod()")
	public void logMethodParams(JoinPoint p) throws Throwable {
    	log.info("CLASS {} METHOD {} INPUT_PARAMS {}", 
    			p.getTarget().getClass().getSimpleName(), 
    			p.getSignature().getName(),
    			p.getArgs());
	}
    
    @Before("controllerRequest()")
	public void logControllerRequest() throws Throwable {
    	ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    	attr.getRequest().setAttribute("API_LOG", "success");
	}
    
    @Around("performanceMethod()")
	public Object logMethodPerformance(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    	MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        
        //Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
         
        final StopWatch stopWatch = new StopWatch();
         
        //Measure method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
 
        //Log method execution time
        log.info("Execution time of " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis() + " ms");
 
        return result;
	}
}