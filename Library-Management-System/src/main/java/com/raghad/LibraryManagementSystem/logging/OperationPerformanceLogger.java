package com.raghad.LibraryManagementSystem.logging;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Order(1)
@Component
public class OperationPerformanceLogger {
    private static final Logger logger = LoggerFactory.getLogger(OperationPerformanceLogger.class);

    @Around("@annotation(com.raghad.LibraryManagementSystem.annotations.OperationPerformanceLogging)")
    public Object logOperationPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object returnValue = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        logger.info("The method " + joinPoint.getSignature().getName()
                + " took " + duration + " milliseconds to execute");

        return returnValue;
    }
}
