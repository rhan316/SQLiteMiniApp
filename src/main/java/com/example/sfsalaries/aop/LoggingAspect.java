package com.example.sfsalaries.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

	// return type class-name.method-name(args)

	@Before("execution(public java.util.Map<String, Integer> com.example.sfsalaries.controller.SalariesController.getAvgPays(..))")
	public void logAvgPaysMethodCall() {
		LOGGER.info("Method getAvgPays() called.");
	}

	@Before("execution(* com.example.sfsalaries.controller.SalariesController.*(..))")
	public void logControllerMethodCall(JoinPoint joinPoint) {
		LOGGER.info("Method called: " + joinPoint.getSignature().getName());
	}
}
