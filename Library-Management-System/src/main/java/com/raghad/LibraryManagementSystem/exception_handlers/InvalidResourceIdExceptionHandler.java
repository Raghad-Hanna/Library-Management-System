package com.raghad.LibraryManagementSystem.exception_handlers;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.raghad.LibraryManagementSystem.exceptions.BookBorrowingException;
import com.raghad.LibraryManagementSystem.exceptions.ResourceIdsMismatchException;
import com.raghad.LibraryManagementSystem.exceptions.CustomException;

@RestControllerAdvice
public class InvalidResourceIdExceptionHandler {
    @ExceptionHandler({ BookBorrowingException.class, ResourceIdsMismatchException.class })
    public ResponseEntity<String> handleBookAndPatronExchangeException(CustomException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
