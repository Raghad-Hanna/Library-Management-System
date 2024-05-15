package com.raghad.LibraryManagementSystem.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
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

    @GetMapping(path = "/{ID}")
    public Book getBookById(@PathVariable("ID") Integer ID) {
        return this.bookService.getBookById(ID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        return this.bookService.createBook(book);
    }

    @PutMapping(path = "/{ID}")
    public Book updateBook(@RequestBody Book book, @PathVariable("ID") Integer ID) {
        return this.bookService.updateBook(book, ID);
    }

    @DeleteMapping(path = "/{ID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("ID") Integer ID) {
        this.bookService.deleteBook(ID);
    }
}
