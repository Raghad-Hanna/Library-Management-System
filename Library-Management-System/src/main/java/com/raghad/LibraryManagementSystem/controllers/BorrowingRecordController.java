package com.raghad.LibraryManagementSystem.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.raghad.LibraryManagementSystem.annotations.ThrownCustomExceptionLogging;

import com.raghad.LibraryManagementSystem.services.BorrowingRecordService;
import com.raghad.LibraryManagementSystem.entities.BorrowingRecord;

@RestController
@RequestMapping(path = "api")
public class BorrowingRecordController {
    private final BorrowingRecordService borrowingRecordService;

    public BorrowingRecordController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    @PostMapping(path = "/borrow/{bookId}/patron/{patronId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ThrownCustomExceptionLogging
    public BorrowingRecord borrowBookByPatron(@PathVariable("bookId") Integer bookId, @PathVariable("patronId") Integer patronId) {
        return this.borrowingRecordService.borrowBookByPatron(bookId, patronId);
    }

    @PutMapping(path = "/return/{bookId}/patron/{patronId}")
    @ThrownCustomExceptionLogging
    public BorrowingRecord returnBookByPatron(@PathVariable("bookId") Integer bookId, @PathVariable("patronId") Integer patronId) {
        return this.borrowingRecordService.returnBookByPatron(bookId, patronId);
    }
}
