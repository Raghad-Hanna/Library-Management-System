package com.raghad.LibraryManagementSystem.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.raghad.LibraryManagementSystem.repositories.PatronRepository;
import com.raghad.LibraryManagementSystem.entities.Patron;
import com.raghad.LibraryManagementSystem.exceptions.ResourceNotFoundException;

@Service
public class PatronService {
    private final PatronRepository patronRepository;

    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public List<Patron> getPatrons() {
        return this.patronRepository.findAll();
    }

    public Patron getPatronById(Integer ID) {
        return this.patronRepository.findById(ID).orElseThrow(() -> new ResourceNotFoundException("Non-existent patron with an ID of "
                + ID + ". Provide an existing patron to be retrieved"));
    }

    @Transactional
    public Patron createPatron(Patron patron) {
        return this.patronRepository.save(patron);
    }

    @Transactional
    public Patron updatePatron(Patron patron, Integer ID) {
        this.patronRepository.findById(ID).orElseThrow(() -> new ResourceNotFoundException("Non-existent patron with an ID of "
                + ID + ". Provide an existing patron to be updated"));
        return this.patronRepository.save(patron);
    }

    @Transactional
    public void deletePatron(Integer ID) {
        this.patronRepository.findById(ID).orElseThrow(() -> new ResourceNotFoundException("Non-existent patron with an ID of "
                + ID + ". Provide an existing patron to be deleted"));
        this.patronRepository.deleteById(ID);
    }
}
