package com.raghad.LibraryManagementSystem;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.http.HttpStatus;
import java.time.LocalDate;

import com.raghad.LibraryManagementSystem.controllers.BorrowingRecordController;
import com.raghad.LibraryManagementSystem.services.BorrowingRecordService;
import com.raghad.LibraryManagementSystem.entities.BorrowingRecord;
import com.raghad.LibraryManagementSystem.entities.Book;
import com.raghad.LibraryManagementSystem.entities.Patron;
import com.raghad.LibraryManagementSystem.exceptions.BookBorrowingException;

class BorrowingRecordControllerTests {
    @InjectMocks
    private BorrowingRecordController borrowingRecordController;

    @Mock
    private BorrowingRecordService borrowingRecordService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void borrowAvailableBookByPatronTest() {
        int bookId = 1;
        var givenBook = new Book(bookId, "Spring Start Here", "Laurentiu Spilca",
                2021, "9781617298691");
        int patronId = 1;
        var givenPatron = new Patron(patronId, "Raghad Hanna",
                "rgh.26d@gmail.com", "0952384185");
        var givenBorrowingRecord = new BorrowingRecord(1, LocalDate.now(),
                BorrowingRecord.BookStatus.BORROWED, givenBook, givenPatron);

        given(this.borrowingRecordService.borrowBookByPatron(bookId, patronId))
                .willReturn(givenBorrowingRecord);

        var returnedBorrowingRecord = this.borrowingRecordController.borrowBookByPatron(bookId, patronId);

        assertEquals(HttpStatus.CREATED, returnedBorrowingRecord.getStatusCode());
        assertEquals(givenBorrowingRecord, returnedBorrowingRecord.getBody());
        verify(this.borrowingRecordService, times(1)).borrowBookByPatron(bookId, patronId);
    }

    @Test
    public void borrowUnavailableBookByPatronTest() {
        int bookId = 2;
        var givenBook = new Book(bookId, "Spring Start Here", "Laurentiu Spilca",
                2021, "9781617298691");
        int patronId = 2;
        var givenPatron = new Patron(patronId, "Raghad Hanna",
                "rgh.26d@gmail.com", "0952384185");

        given(this.borrowingRecordService.borrowBookByPatron(bookId, patronId))
                .willThrow(new BookBorrowingException(""));

        assertThrows(
                BookBorrowingException.class,
                () -> borrowingRecordController.borrowBookByPatron(bookId, patronId)
        );
    }

    @Test
    public void returnUnavailableBookByPatronTest() {
        int bookId = 3;
        var givenBook = new Book(bookId, "The Unbearable Lightness Of Being",
                "Milan Kundera", 1984, "ISBN-2");
        int patronId = 3;
        var givenPatron = new Patron(patronId, "Edward Norton",
                "edward.norton@gmail.com", "0986574354");
        var givenBorrowingRecord = new BorrowingRecord(1, LocalDate.now(),
                BorrowingRecord.BookStatus.RETURNED, givenBook, givenPatron);

        given(this.borrowingRecordService.returnBookByPatron(bookId, patronId)).willReturn(givenBorrowingRecord);

        var returnedBorrowingRecord = this.borrowingRecordController.returnBookByPatron(bookId, patronId);

        assertEquals(HttpStatus.OK, returnedBorrowingRecord.getStatusCode());
        assertEquals(givenBorrowingRecord, returnedBorrowingRecord.getBody());
        verify(this.borrowingRecordService, times(1)).returnBookByPatron(bookId, patronId);
    }

    @Test
    public void returnAvailableBookByPatronTest() {
        int bookId = 4;
        var givenBook = new Book(bookId, "Spring Start Here", "Laurentiu Spilca",
                2021, "9781617298691");
        int patronId = 4;
        var givenPatron = new Patron(patronId, "Raghad Hanna",
                "rgh.26d@gmail.com", "0952384185");

        given(this.borrowingRecordService.returnBookByPatron(bookId, patronId))
                .willThrow(new BookBorrowingException(""));

        assertThrows(
                BookBorrowingException.class,
                () -> borrowingRecordController.returnBookByPatron(bookId, patronId)
        );
    }
}