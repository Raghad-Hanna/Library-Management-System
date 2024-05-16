package com.raghad.LibraryManagementSystem.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import java.util.List;

import com.raghad.LibraryManagementSystem.repositories.BookRepository;
import com.raghad.LibraryManagementSystem.entities.Book;
import com.raghad.LibraryManagementSystem.exceptions.ResourceNotFoundException;
import com.raghad.LibraryManagementSystem.annotations.OperationPerformanceLogging;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return this.bookRepository.findAll();
    }

    @Cacheable("Book")
    public Book getBookById(Integer id) {
        return this.bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Non-existent book with an id of "
                + id + ". Provide an existing book"));
    }

    @Transactional
    @OperationPerformanceLogging
    public Book createBook(Book book) {
        return this.bookRepository.save(book);
    }

    @Transactional
    @CacheEvict(value = "Book")
    @OperationPerformanceLogging
    public Book updateBook(Book book, Integer id) {
        this.bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Non-existent book with an id of "
                + id + ". Provide an existing book"));
        return this.bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(Integer id) {
        this.bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Non-existent book with an id of "
                + id + ". Provide an existing book"));
        this.bookRepository.deleteById(id);
    }
}
