package com.raghad.LibraryManagementSystem;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import org.springframework.http.HttpStatus;
import java.util.List;

import com.raghad.LibraryManagementSystem.controllers.PatronController;
import com.raghad.LibraryManagementSystem.services.PatronService;
import com.raghad.LibraryManagementSystem.entities.Patron;
import com.raghad.LibraryManagementSystem.exceptions.ResourceNotFoundException;

class PatronControllerTests {
    @InjectMocks
    private PatronController patronController;

    @Mock
    private PatronService patronService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getPatronsTest() {
        var givenPatrons = List.of(
                new Patron(1, "Edward Norton",
                        "edward.norton@gmail.com", "0986574354"),
                new Patron(2, "Raghad Hanna",
                        "rgh.26d@gmail.com", "0952384185")
        );

        given(patronService.getPatrons()).willReturn(givenPatrons);

        var returnedPatrons = patronController.getPatrons();

        assertEquals(HttpStatus.OK, returnedPatrons.getStatusCode());
        assertEquals(givenPatrons, returnedPatrons.getBody());
        verify(this.patronService, times(1)).getPatrons();
    }

    @Test
    public void getExistentPatronByIdTest() {
        int patronId = 3;
        var givenPatron = new Patron(patronId, "Naomi Watts",
                "naomi.watts@gmail.com", "0998877665");

        given(this.patronService.getPatronById(patronId)).willReturn(givenPatron);

        var returnedPatron = patronController.getPatronById(patronId);

        assertEquals(HttpStatus.OK, returnedPatron.getStatusCode());
        assertEquals(givenPatron, returnedPatron.getBody());
        verify(this.patronService, times(1)).getPatronById(patronId);
    }

    @Test
    public void getNonExistentPatronByIdTest() {
        int patronId = -1;
        String exceptionMessage = "Non-existent patron with an id of "
                + patronId + ". Provide an existing patron";

        given(this.patronService.getPatronById(patronId))
                .willThrow(new ResourceNotFoundException(exceptionMessage));

        var exception = assertThrows(
                ResourceNotFoundException.class,
                () -> patronController.getPatronById(patronId)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    public void createPatronTest() {
        var givenPatron = new Patron(4, "Mike Flanagan",
                "mike.flanagan@gmail.com", "0998765543");

        given(this.patronService.createPatron(givenPatron)).willReturn(givenPatron);

        var returnedPatron = this.patronController.createPatron(givenPatron);

        assertEquals(HttpStatus.CREATED, returnedPatron.getStatusCode());
        assertEquals(givenPatron, returnedPatron.getBody());
        verify(this.patronService, times(1)).createPatron(givenPatron);
    }

    @Test
    public void updateExistentPatronTest() {
        int patronId = 5;
        var givenPatron = new Patron(patronId, "Roman Polanski",
                "roman.polanski@gmail.com", "0999887766");

        given(this.patronService.updatePatron(givenPatron, patronId)).willReturn(givenPatron);

        var returnedPatron = this.patronController.updatePatron(givenPatron, patronId);

        assertEquals(HttpStatus.OK, returnedPatron.getStatusCode());
        assertEquals(givenPatron, returnedPatron.getBody());
        verify(this.patronService, times(1)).updatePatron(givenPatron, patronId);
    }

    @Test
    public void updateNonExistentPatronTest() {
        int patronId = -1;
        var givenPatron = new Patron(patronId, "David Lynch",
                "david.lynch@gmail.com", "0943567866");
        String exceptionMessage = "Non-existent patron with an id of "
                + patronId + ". Provide an existing patron";

        given(this.patronService.updatePatron(givenPatron, patronId))
                .willThrow(new ResourceNotFoundException(exceptionMessage));

        var exception = assertThrows(
                ResourceNotFoundException.class,
                () -> patronController.updatePatron(givenPatron, patronId)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    public void deleteExistentPatronTest() {
        int patronId = 6;

        doNothing().when(this.patronService).deletePatron(patronId);

        var returnedResponse = this.patronController.deletePatron(patronId);

        assertEquals(HttpStatus.NO_CONTENT, returnedResponse.getStatusCode());
        assertNull(returnedResponse.getBody());
    }

    @Test
    public void deleteNonExistentPatronTest() {
        int patronId = -1;
        String exceptionMessage = "Non-existent patron with an id of "
                + patronId + ". Provide an existing patron";

        doThrow(new ResourceNotFoundException(exceptionMessage))
                .when(this.patronService).deletePatron(patronId);

        var exception = assertThrows(
                ResourceNotFoundException.class,
                () -> patronController.deletePatron(patronId)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }
}