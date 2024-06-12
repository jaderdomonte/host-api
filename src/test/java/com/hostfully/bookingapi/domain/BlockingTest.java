package com.hostfully.bookingapi.domain;

import com.hostfully.bookingapi.exceptions.DomainObjectValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BlockingTest {

    @Test
    void shouldThrowsExceptionIfCreateDomainObjectWithoutProperty() {
        DomainObjectValidationException exception = assertThrows(DomainObjectValidationException.class, () -> {
            PeriodVO periodVO = new PeriodVO(LocalDate.now(), LocalDate.now().plusDays(10));
            new Blocking(null, periodVO);
        });

        assertEquals("Property is required.", exception.getMessage());
    }

    @Test
    void shouldThrowsExceptionIfCreateDomainObjectWithoutPeriod() {
        DomainObjectValidationException exception = assertThrows(DomainObjectValidationException.class, () -> {
            Property property = new Property(1L);
            new Blocking(property, null);
        });

        assertEquals("Period is required.", exception.getMessage());
    }

    @Test
    void shouldThrowsExceptionIfCreateDomainObjectWithoutRequiredFields() {
        assertThrows(DomainObjectValidationException.class, () -> {
            new Blocking(null, null);
        });
    }

    @Test
    void shouldCreateDomainObjectWithRequiredFields() {
        Property property = new Property(1L);
        PeriodVO periodVO = new PeriodVO(LocalDate.now(), LocalDate.now().plusDays(10));
        new Blocking(property, periodVO);

        assertDoesNotThrow(() -> DomainObjectValidationException.class);
    }
}