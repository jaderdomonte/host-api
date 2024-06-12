package com.hostfully.bookingapi.domain;

import com.hostfully.bookingapi.exceptions.DomainObjectValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GuestTest {

    @Test
    void shouldThrowsExceptionIfCreateDomainObjectWithoutRequiredFields() {
        DomainObjectValidationException exception = assertThrows(DomainObjectValidationException.class, () -> {
            new Guest(null, null);
        });

        assertEquals("Guest fullName is required.", exception.getMessage());
    }


    @Test
    void shouldCreateDomainObjectWithRequiredFields() {
        GuestNameVO guestNameVO = new GuestNameVO("Mike", "Evans");
        new Guest(1L, guestNameVO);

        assertDoesNotThrow(() -> DomainObjectValidationException.class);
    }
}