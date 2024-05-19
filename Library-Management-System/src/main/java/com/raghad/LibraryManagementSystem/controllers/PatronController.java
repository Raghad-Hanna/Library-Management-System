package com.raghad.LibraryManagementSystem.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import java.util.List;

import com.raghad.LibraryManagementSystem.services.PatronService;
import com.raghad.LibraryManagementSystem.entities.Patron;
import com.raghad.LibraryManagementSystem.annotations.ThrownCustomExceptionLogging;
import com.raghad.LibraryManagementSystem.exceptions.ResourceIdsMismatchException;

@RestController
@RequestMapping(path = "api/patrons")
public class PatronController {
    private final PatronService patronService;

    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping
    public ResponseEntity<List<Patron>> getPatrons() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.patronService.getPatrons());
    }

    @GetMapping(path = "/{id}")
    @ThrownCustomExceptionLogging
    public ResponseEntity<Patron> getPatronById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.patronService.getPatronById(id));
    }

    @PostMapping
    public ResponseEntity<Patron> createPatron(@Valid @RequestBody Patron patron) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.patronService.createPatron(patron));
    }

    @PutMapping(path = "/{id}")
    @ThrownCustomExceptionLogging
    public ResponseEntity<Patron> updatePatron(@Valid @RequestBody Patron patron, @PathVariable("id") Integer id) {
        if(patron.getId() != id)
            throw new ResourceIdsMismatchException("The patron ids "
                    + " in the URI and in the request payload must match");

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.patronService.updatePatron(patron, id));
    }

    @DeleteMapping(path = "/{id}")
    @ThrownCustomExceptionLogging
    public ResponseEntity<Patron> deletePatron(@PathVariable("id") Integer id) {
        this.patronService.deletePatron(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}
