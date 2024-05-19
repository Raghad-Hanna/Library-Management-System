package com.raghad.LibraryManagementSystem.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import java.util.List;

import com.raghad.LibraryManagementSystem.services.BookService;
import com.raghad.LibraryManagementSystem.entities.Book;
import com.raghad.LibraryManagementSystem.annotations.ThrownCustomExceptionLogging;
import com.raghad.LibraryManagementSystem.exceptions.ResourceIdsMismatchException;

@RestController
@RequestMapping(path = "api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.bookService.getBooks());
    }

    @GetMapping(path = "/{id}")
    @ThrownCustomExceptionLogging
    public ResponseEntity<Book> getBookById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.bookService.getBookById(id));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.bookService.createBook(book));
    }

    @PutMapping(path = "/{id}")
    @ThrownCustomExceptionLogging
    public ResponseEntity<Book> updateBook(@Valid @RequestBody Book book, @PathVariable("id") Integer id) {
        if(book.getId() != id)
            throw new ResourceIdsMismatchException("The book ids"
                    + " in the URI and in the request payload must match");

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.bookService.updateBook(book, id));
    }

    @DeleteMapping(path = "/{id}")
    @ThrownCustomExceptionLogging
    public ResponseEntity<Book> deleteBook(@PathVariable("id") Integer id) {
        this.bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}
