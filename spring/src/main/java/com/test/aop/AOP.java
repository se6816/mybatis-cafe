package com.test.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import com.test.domain.UserVO;


@EnableAspectJAutoProxy
@Component
@Aspect
public class AOP {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Around(value = "execution(* com.test.Service..*.*(..))")
	public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println(joinPoint.getSignature().getDeclaringTypeName()+"->"+joinPoint.getSignature().getName());
		Object result=joinPoint.proceed();
		System.out.println("finish-"+joinPoint.getSignature().getDeclaringTypeName()+ "/"+joinPoint.getSignature().getName());
		
		return result;
	}
	@Around(value = "execution(* com.test.Contoller..*.*(..))")
	public Object gg(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println(joinPoint.getSignature().getDeclaringTypeName()+"->"+joinPoint.getSignature().getName());
		Object result=joinPoint.proceed();
		System.out.println("finish-"+joinPoint.getSignature().getDeclaringTypeName()+ "/"+joinPoint.getSignature().getName());
		
		return result;
	}
	
	
	

}

