package com.raghad.LibraryManagementSystem.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
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

    @GetMapping(path = "/{ID}")
    public Patron getPatronById(@PathVariable("ID") Integer ID) {
        return this.patronService.getPatronById(ID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patron createPatron(@RequestBody Patron patron) {
        return this.patronService.createPatron(patron);
    }

    @PutMapping(path = "/{ID}")
    public Patron updatePatron(@RequestBody Patron patron, @PathVariable("ID") Integer ID) {
        return this.patronService.updatePatron(patron, ID);
    }

    @DeleteMapping(path = "/{ID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatron(@PathVariable("ID") Integer ID) {
        this.patronService.deletePatron(ID);
    }
}
