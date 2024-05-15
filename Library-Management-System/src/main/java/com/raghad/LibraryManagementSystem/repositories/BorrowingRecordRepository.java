package com.raghad.LibraryManagementSystem.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.raghad.LibraryManagementSystem.entities.BorrowingRecord;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Integer> {
    List<BorrowingRecord> findByBookId(Integer id);
}
