package com.raghad.LibraryManagementSystem.logging;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;

@Aspect
@Order(2)
@Component
public class MethodCallLogger {
    private static final Logger logger = LoggerFactory.getLogger(MethodCallLogger.class);

    @Around("execution(* com.raghad.LibraryManagementSystem.services.*.*(..))" +
            "|| execution(* com.raghad.LibraryManagementSystem.repositories.*.*(..))")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        var targetClassFullyQualifiedName = joinPoint.getTarget().getClass().getName();
        var methodIdentifier = joinPoint.getSignature().getName();
        Object[] methodArguments = joinPoint.getArgs();

        Object methodReturnValue;
        String logMessage = "\n" + targetClassFullyQualifiedName + "." + methodIdentifier + "()"
                + "\ncalled with arguments: " + Arrays.stream(methodArguments).toList();

        try {
            methodReturnValue = joinPoint.proceed();
            logger.info(logMessage
                    + "\nreturned: " + ((methodReturnValue != null) ? methodReturnValue: "void"));
        }
        catch(Throwable th) {
            logger.info(logMessage);
            throw th;
        }
        return methodReturnValue;
    }
}
