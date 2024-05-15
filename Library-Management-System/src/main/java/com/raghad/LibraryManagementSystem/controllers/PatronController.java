package com.raghad.LibraryManagementSystem.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import java.util.List;

import com.raghad.LibraryManagementSystem.services.PatronService;
import com.raghad.LibraryManagementSystem.entities.Patron;

@RestController
@RequestMapping(path = "api/patrons")
public class PatronController {
    private final PatronService patronService;

    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping
    public List<Patron> getPatrons() {
        return this.patronService.getPatrons();
    }

    @GetMapping(path = "/{id}")
    public Patron getPatronById(@PathVariable("id") Integer id) {
        return this.patronService.getPatronById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patron createPatron(@Valid @RequestBody Patron patron) {
        return this.patronService.createPatron(patron);
    }

    @PutMapping(path = "/{id}")
    public Patron updatePatron(@Valid @RequestBody Patron patron, @PathVariable("id") Integer id) {
        return this.patronService.updatePatron(patron, id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatron(@PathVariable("id") Integer id) {
        this.patronService.deletePatron(id);
    }
}
