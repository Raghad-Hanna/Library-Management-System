package com.raghad.LibraryManagementSystem;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import org.springframework.http.HttpStatus;
import java.util.List;

import com.raghad.LibraryManagementSystem.controllers.BookController;
import com.raghad.LibraryManagementSystem.services.BookService;
import com.raghad.LibraryManagementSystem.entities.Book;
import com.raghad.LibraryManagementSystem.exceptions.ResourceNotFoundException;

class BookControllerTests {
    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getBooksTest() {
        var givenBooks = List.of(
                new Book(1, "Spring Start Here", "Laurentiu Spilca",
                        2021, "9781617298691"),
                new Book(2, "The Unbearable Lightness Of Being", "Milan Kundera",
                        1984, "ISBN-2")
        );

        given(bookService.getBooks()).willReturn(givenBooks);

        var returnedBooks = bookController.getBooks();

        assertEquals(HttpStatus.OK, returnedBooks.getStatusCode());
        assertEquals(givenBooks, returnedBooks.getBody());
        verify(this.bookService, times(1)).getBooks();
    }

    @Test
    public void getExistentBookByIdTest() {
        int bookId = 3;
        var givenBook = new Book(bookId, "Misery", "Stephen King",
                1987, "ISBN-3");

        given(this.bookService.getBookById(bookId)).willReturn(givenBook);

        var returnedBook = bookController.getBookById(bookId);

        assertEquals(HttpStatus.OK, returnedBook.getStatusCode());
        assertEquals(givenBook, returnedBook.getBody());
        verify(this.bookService, times(1)).getBookById(bookId);
    }

    @Test
    public void getNonExistentBookByIdTest() {
        int bookId = -1;
        String exceptionMessage = "Non-existent book with an id of "
                + bookId + ". Provide an existing book";

        given(this.bookService.getBookById(bookId))
                .willThrow(new ResourceNotFoundException(exceptionMessage));

        var exception = assertThrows(
                ResourceNotFoundException.class,
                () -> bookController.getBookById(bookId)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    public void createBookTest() {
        var givenBook = new Book(4, "dummy name", "dummy author",
                2024, "ISBN-4");

        given(this.bookService.createBook(givenBook)).willReturn(givenBook);

        var returnedBook = this.bookController.createBook(givenBook);

        assertEquals(HttpStatus.CREATED, returnedBook.getStatusCode());
        assertEquals(givenBook, returnedBook.getBody());
        verify(this.bookService, times(1)).createBook(givenBook);
    }

    @Test
    public void updateExistentBookTest() {
        int bookId = 5;
        var givenBook = new Book(bookId, "Java For Dummies", "Barry Burd",
                2000, "ISBN-5");

        given(this.bookService.updateBook(givenBook, bookId)).willReturn(givenBook);

        var returnedBook = this.bookController.updateBook(givenBook, bookId);

        assertEquals(HttpStatus.OK, returnedBook.getStatusCode());
        assertEquals(givenBook, returnedBook.getBody());
        verify(this.bookService, times(1)).updateBook(givenBook, bookId);
    }

    @Test
    public void updateNonExistentBookTest() {
        int bookId = -1;
        var givenBook = new Book(bookId, "The Apocalypse", "B.H.A",
                2025, "ISBN-5");
        String exceptionMessage = "Non-existent book with an id of "
                + bookId + ". Provide an existing book";

        given(this.bookService.updateBook(givenBook, bookId))
                .willThrow(new ResourceNotFoundException(exceptionMessage));

        var exception = assertThrows(
                ResourceNotFoundException.class,
                () -> bookController.updateBook(givenBook, bookId)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    public void deleteExistentBookTest() {
        int bookId = 6;

        doNothing().when(this.bookService).deleteBook(bookId);

        var returnedResponse = this.bookController.deleteBook(bookId);

        assertEquals(HttpStatus.NO_CONTENT, returnedResponse.getStatusCode());
        assertNull(returnedResponse.getBody());
    }

    @Test
    public void deleteNonExistentBookTest() {
        int bookId = -1;
        String exceptionMessage = "Non-existent book with an id of "
                + bookId + ". Provide an existing book";

        doThrow(new ResourceNotFoundException(exceptionMessage))
                .when(this.bookService).deleteBook(bookId);

        var exception = assertThrows(
                ResourceNotFoundException.class,
                () -> bookController.deleteBook(bookId)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }
}