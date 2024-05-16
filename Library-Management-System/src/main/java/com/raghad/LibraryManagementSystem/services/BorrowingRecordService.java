package com.raghad.LibraryManagementSystem.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

import com.raghad.LibraryManagementSystem.repositories.BorrowingRecordRepository;
import com.raghad.LibraryManagementSystem.entities.BorrowingRecord;
import com.raghad.LibraryManagementSystem.entities.BorrowingRecord.BookStatus;
import com.raghad.LibraryManagementSystem.exceptions.BookBorrowingException;
import com.raghad.LibraryManagementSystem.annotations.OperationPerformanceLogging;

@Service
public class BorrowingRecordService {
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookService bookService;
    private final PatronService patronService;

    public BorrowingRecordService(BorrowingRecordRepository borrowingRecordRepository,
                                  BookService bookService, PatronService patronService) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookService = bookService;
        this.patronService = patronService;
    }

    @Transactional
    @OperationPerformanceLogging
    public BorrowingRecord borrowBookByPatron(Integer bookId, Integer patronId) {
        var existingBook = this.bookService.getBookById(bookId);
        var existingPatron = this.patronService.getPatronById(patronId);
        var borrowingRecords = this.borrowingRecordRepository.findByBookId(bookId);

        var borrowingRecord = borrowingRecords.stream()
                .skip(borrowingRecords.size() > 0 ? (borrowingRecords.size()-1) : 0)
                .findFirst();

        if(borrowingRecord.isPresent() &&
                borrowingRecord.get().getBookStatus() == BookStatus.BORROWED)
            throw new BookBorrowingException("The book with an id of " + bookId + " is already borrowed"
                    + " by a patron with an id of " + borrowingRecord.get().getPatron().getId());

        var createdBorrowingRecord = new BorrowingRecord(LocalDate.now(), BookStatus.BORROWED, existingBook,
                existingPatron);
        return this.borrowingRecordRepository.save(createdBorrowingRecord);
    }

    @Transactional
    @OperationPerformanceLogging
    public BorrowingRecord returnBookByPatron(Integer bookId, Integer patronId) {
        var existingBook = this.bookService.getBookById(bookId);
        var existingPatron = this.patronService.getPatronById(patronId);
        var borrowingRecords = this.borrowingRecordRepository.findByBookId(bookId);

        var borrowingRecord = borrowingRecords.stream()
                .skip(borrowingRecords.size() > 0 ? (borrowingRecords.size()-1) : 0)
                .findFirst();

        if(borrowingRecord.isEmpty() ||
                borrowingRecord.get().getBookStatus() == BookStatus.RETURNED) {
            throw new BookBorrowingException("The book with an id of " + bookId + " is not borrowed");
        }

        if(borrowingRecord.get().getPatron().getId() != patronId) {
            throw new BookBorrowingException("The book with an id of " + bookId + " is not borrowed"
                    + " by the patron with an id of " + patronId);
        }

        var updatedBorrowingRecord = new BorrowingRecord(LocalDate.now(),
                BookStatus.RETURNED, existingBook, existingPatron);
        return this.borrowingRecordRepository.save(updatedBorrowingRecord);
    }
}
