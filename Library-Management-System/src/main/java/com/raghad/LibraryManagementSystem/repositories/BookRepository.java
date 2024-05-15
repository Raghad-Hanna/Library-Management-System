package com.raghad.LibraryManagementSystem.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.raghad.LibraryManagementSystem.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
