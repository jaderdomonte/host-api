package com.hostfully.bookingapi.domain;

import com.hostfully.bookingapi.exceptions.DomainObjectValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GuestNameVOTest {

    @Test
    void shouldThrowsExceptionIfCreateDomainObjectWithoutRequiredFields() {
        DomainObjectValidationException exception = assertThrows(DomainObjectValidationException.class, () -> {
            new GuestNameVO(null, null);
        });

        assertEquals("Guest firstName and lastName are required.", exception.getMessage());
    }


    @Test
    void shouldCreateDomainObjectWithRequiredFields() {
        new GuestNameVO("Mike", "Evans");

        assertDoesNotThrow(() -> DomainObjectValidationException.class);
    }
}