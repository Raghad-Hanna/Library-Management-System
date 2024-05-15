package com.raghad.LibraryManagementSystem.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import com.raghad.LibraryManagementSystem.annotations.ThrownCustomExceptionLogging;
import java.util.List;

import com.raghad.LibraryManagementSystem.services.BookService;
import com.raghad.LibraryManagementSystem.entities.Book;

@RestController
@RequestMapping(path = "api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks() {
        return this.bookService.getBooks();
    }

    @GetMapping(path = "/{id}")
    @ThrownCustomExceptionLogging
    public Book getBookById(@PathVariable("id") Integer id) {
        return this.bookService.getBookById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@Valid @RequestBody Book book) {
        return this.bookService.createBook(book);
    }

    @PutMapping(path = "/{id}")
    @ThrownCustomExceptionLogging
    public Book updateBook(@Valid @RequestBody Book book, @PathVariable("id") Integer id) {
        return this.bookService.updateBook(book, id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ThrownCustomExceptionLogging
    public void deleteBook(@PathVariable("id") Integer id) {
        this.bookService.deleteBook(id);
    }
}
