package com.hostfully.bookingapi.domain;

import com.hostfully.bookingapi.db.enumeration.BookingStatusEnum;
import com.hostfully.bookingapi.exceptions.DomainObjectValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookingStatusVOTest {

    @Test
    void shouldThrowsExceptionIfCreateDomainObjectWithoutRequiredFields() {
        DomainObjectValidationException exception = assertThrows(DomainObjectValidationException.class, () -> {
            new BookingStatusVO(1l, null);
        });

        assertEquals("Description is required.", exception.getMessage());
    }

    @Test
    void shouldCreateDomainObjectWithRequiredFields() {
        new BookingStatusVO(BookingStatusEnum.CANCELED.getId(), BookingStatusEnum.CANCELED.getDescription());

        assertDoesNotThrow(() -> DomainObjectValidationException.class);
    }
}