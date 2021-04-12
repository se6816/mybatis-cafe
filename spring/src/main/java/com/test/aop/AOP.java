package com.test.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AOP {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Around(value = "* com.test..*.*(..)")
	public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println(joinPoint.getSignature().getDeclaringTypeName()+ "/"+joinPoint.getSignature().getName());
		Object result=joinPoint.proceed();
		System.out.println("finish-"+joinPoint.getSignature().getDeclaringTypeName()+ "/"+joinPoint.getSignature().getName());
		return result;
	}

}

