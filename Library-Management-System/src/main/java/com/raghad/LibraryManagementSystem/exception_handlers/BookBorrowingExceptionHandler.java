package com.raghad.LibraryManagementSystem.exception_handlers;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.raghad.LibraryManagementSystem.exceptions.BookBorrowingException;

@RestControllerAdvice
public class BookBorrowingExceptionHandler {
    @ExceptionHandler(BookBorrowingException.class)
    public ResponseEntity<String> handleBookAndPatronExchangeException(BookBorrowingException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
