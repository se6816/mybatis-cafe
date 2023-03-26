package com.cafe.classic.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;



@EnableAspectJAutoProxy
@Aspect
@Component
public class LogAspect {
	private Logger logger = LoggerFactory.getLogger(LogAspect.class);

	@Around(value = "execution(* com.cafe.classic.Service..*.*(..))")
	public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
		
		logger.debug("start-{} -> {}",joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());
		Object result=joinPoint.proceed();
		logger.debug("finish-{} -> {}",joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());	
		return result;
	}
	@Around(value = "execution(* com.cafe.classic.Controller..*.*(..))")
	public Object logging2(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.debug("start-{} -> {}",joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());
		Object result=joinPoint.proceed();
		logger.debug("finish-{} -> {}",joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());	
		return result;
	}
	
	

}

