package com.raghad.LibraryManagementSystem.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.raghad.LibraryManagementSystem.repositories.BookRepository;
import com.raghad.LibraryManagementSystem.entities.Book;
import com.raghad.LibraryManagementSystem.exceptions.ResourceNotFoundException;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return this.bookRepository.findAll();
    }

    public Book getBookById(Integer ID) {
        return this.bookRepository.findById(ID).orElseThrow(() -> new ResourceNotFoundException("Non-existent book with an ID of "
                + ID + ". Provide an existing client to be retrieved"));
    }

    @Transactional
    public Book createBook(Book book) {
        return this.bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(Book book, Integer ID) {
        Book existingBook = this.bookRepository.findById(ID).orElseThrow(() -> new ResourceNotFoundException("Non-existent book with an ID of "
                + ID + ". Provide an existing client to be updated"));
        return this.bookRepository.save(existingBook);
    }

    @Transactional
    public void deleteBook(Integer ID) {
        this.bookRepository.findById(ID).orElseThrow(() -> new ResourceNotFoundException("Non-existent book with an ID of "
                + ID + ". Provide an existing client to be deleted"));
        this.bookRepository.deleteById(ID);
    }
}
