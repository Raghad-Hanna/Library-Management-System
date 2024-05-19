package com.raghad.LibraryManagementSystem.logging;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raghad.LibraryManagementSystem.exceptions.CustomException;

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
