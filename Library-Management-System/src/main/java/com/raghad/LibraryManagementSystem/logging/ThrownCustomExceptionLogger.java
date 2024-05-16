package com.raghad.LibraryManagementSystem.logging;

import com.raghad.LibraryManagementSystem.exceptions.CustomException;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Order(3)
@Component
public class ThrownCustomExceptionLogger {
    private static final Logger logger = LoggerFactory.getLogger(ThrownCustomExceptionLogger.class);

    @AfterThrowing(value = "@annotation(com.raghad.LibraryManagementSystem.annotations.ThrownCustomExceptionLogging)", throwing = "ex")
    public void logThrownException(CustomException ex) {
        logger.error(ex.getClass().getName() + " is thrown - " + ex.getMessage());
    }
}
