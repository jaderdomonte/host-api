package com.hostfully.bookingapi.domain;

import com.hostfully.bookingapi.exceptions.DomainObjectValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PropertyTest {

    @Test
    void shouldThrowsExceptionIfCreateDomainObjectWithoutRequiredFields() {
        DomainObjectValidationException exception = assertThrows(DomainObjectValidationException.class, () -> {
            new Property(null, null);
        });

        assertEquals("Property name is required.", exception.getMessage());
    }


    @Test
    void shouldCreateDomainObjectWithRequiredFields() {
        new Property(1L, "Beach House");

        assertDoesNotThrow(() -> DomainObjectValidationException.class);
    }
}