package com.raghad.LibraryManagementSystem.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import java.util.List;

import com.raghad.LibraryManagementSystem.repositories.PatronRepository;
import com.raghad.LibraryManagementSystem.entities.Patron;
import com.raghad.LibraryManagementSystem.exceptions.ResourceNotFoundException;
import com.raghad.LibraryManagementSystem.annotations.OperationPerformanceLogging;

@Service
public class PatronService {
    private final PatronRepository patronRepository;

    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public List<Patron> getPatrons() {
        return this.patronRepository.findAll();
    }

    @Cacheable("Patron")
    public Patron getPatronById(Integer id) {
        return this.patronRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Non-existent patron"
                        + " with an id of " + id + ". Provide an existing patron"));
    }

    @OperationPerformanceLogging
    public Patron createPatron(Patron patron) {
        return this.patronRepository.save(patron);
    }

    @Transactional
    @CacheEvict(value = "Patron")
    @OperationPerformanceLogging
    public Patron updatePatron(Patron patron, Integer id) {
        this.patronRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Non-existent patron"
                        + " with an id of " + id + ". Provide an existing patron"));

        return this.patronRepository.save(patron);
    }

    @Transactional
    public void deletePatron(Integer id) {
        this.patronRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Non-existent patron"
                        + " with an ID of " + id + ". Provide an existing patron"));

        this.patronRepository.deleteById(id);
    }
}
