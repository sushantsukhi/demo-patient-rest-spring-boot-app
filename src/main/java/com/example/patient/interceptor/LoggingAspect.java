package com.example.patient.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {
	private static final Logger LOG = LogManager.getLogger(LoggingAspect.class);

	@Around("execution(* com.example.patient..*(..)))"
			+ "&& !@annotation(com.example.patient.interceptor.NoLogging)"
			+ "&& !@target(com.example.patient.interceptor.NoLogging)")
	public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

		// Get intercepted method details
		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();
		String methodDef = className + "." + methodName + "()";
		final StopWatch stopWatch = new StopWatch();

		LOG.info("::: STARTED: " + methodDef);

		// Measure method execution time
		stopWatch.start();
		Object result = proceedingJoinPoint.proceed();
		stopWatch.stop();

		// Log method execution time
		LOG.info("::: ENDED: " + methodDef + ", Execution time taken " + stopWatch.getTotalTimeMillis() + " ms");

		return result;
	}
}
