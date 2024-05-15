package com.raghad.LibraryManagementSystem.exception_handlers;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;

import com.raghad.LibraryManagementSystem.exceptions.ResourceNotFoundException;
import com.raghad.LibraryManagementSystem.DTOs.ErrorDetail;
import com.raghad.LibraryManagementSystem.DTOs.ErrorResponse;

@RestControllerAdvice
public class ResourceNotFoundExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        String description = ex.getMessage();
        var errorDetails = this.getErrorDetails();

        var errorResponse = new ErrorResponse(description, errorDetails);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    private List<ErrorDetail> getErrorDetails() {
        return List.of(new ErrorDetail("id",
                "The resource id must exist"));
    }
}

