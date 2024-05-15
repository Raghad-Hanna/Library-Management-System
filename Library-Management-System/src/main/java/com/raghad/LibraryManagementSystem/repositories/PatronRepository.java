package com.raghad.LibraryManagementSystem.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.raghad.LibraryManagementSystem.entities.Patron;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Integer> {
}
