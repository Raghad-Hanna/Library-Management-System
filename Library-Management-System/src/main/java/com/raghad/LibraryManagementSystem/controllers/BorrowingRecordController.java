package com.raghad.LibraryManagementSystem.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;

import com.raghad.LibraryManagementSystem.services.BorrowingRecordService;
import com.raghad.LibraryManagementSystem.entities.BorrowingRecord;
import com.raghad.LibraryManagementSystem.annotations.ThrownCustomExceptionLogging;

@RestController
@RequestMapping(path = "api")
public class BorrowingRecordController {
    private final BorrowingRecordService borrowingRecordService;

    public BorrowingRecordController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    @PostMapping(path = "/borrow/{bookId}/patron/{patronId}")
    @Operation(summary = "Borrow a book with an id by a patron with an id")
    @ThrownCustomExceptionLogging
    public ResponseEntity<BorrowingRecord> borrowBookByPatron(@PathVariable("bookId") Integer bookId,
                                                              @PathVariable("patronId") Integer patronId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.borrowingRecordService.borrowBookByPatron(bookId, patronId));
    }

    @PutMapping(path = "/return/{bookId}/patron/{patronId}")
    @Operation(summary = "return a book with an id by a patron with an id")
    @ThrownCustomExceptionLogging
    public ResponseEntity<BorrowingRecord> returnBookByPatron(@PathVariable("bookId") Integer bookId,
                                                              @PathVariable("patronId") Integer patronId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.borrowingRecordService.returnBookByPatron(bookId, patronId));
    }
}
