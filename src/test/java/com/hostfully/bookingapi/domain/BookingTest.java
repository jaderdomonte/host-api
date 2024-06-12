package com.hostfully.bookingapi.domain;

import com.hostfully.bookingapi.db.enumeration.BookingStatusEnum;
import com.hostfully.bookingapi.exceptions.DomainObjectValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookingTest {

    @Test
    void shouldThrowsExceptionIfCreateDomainObjectWithoutGuest() {
        DomainObjectValidationException exception = assertThrows(DomainObjectValidationException.class, () -> {
            GuestNameVO guestNameVO = new GuestNameVO("Jordan", "Love");
            Property property = new Property(1L, "Beach House");
            PeriodVO periodVO = new PeriodVO(LocalDate.now(), LocalDate.now().plusDays(10));
            BookingStatusVO bookingStatusVO = new BookingStatusVO(BookingStatusEnum.CANCELED.getId(), BookingStatusEnum.CANCELED.getDescription());

            new Booking(1L, null, property, periodVO, bookingStatusVO);
        });

        assertEquals("Guest is required.", exception.getMessage());
    }

    @Test
    void shouldThrowsExceptionIfCreateDomainObjectWithoutPeriod() {
        DomainObjectValidationException exception = assertThrows(DomainObjectValidationException.class, () -> {
            GuestNameVO guestNameVO = new GuestNameVO("Jordan", "Love");
            Guest guest = new Guest(1L, guestNameVO);
            Property property = new Property(1L, "Beach House");
            BookingStatusVO bookingStatusVO = new BookingStatusVO(BookingStatusEnum.CANCELED.getId(), BookingStatusEnum.CANCELED.getDescription());

            new Booking(1L, guest, property, null, bookingStatusVO);
        });

        assertEquals("Period is required.", exception.getMessage());
    }

    @Test
    void shouldThrowsExceptionIfCreateDomainObjectWithoutProperty() {
        DomainObjectValidationException exception = assertThrows(DomainObjectValidationException.class, () -> {
            GuestNameVO guestNameVO = new GuestNameVO("Jordan", "Love");
            Guest guest = new Guest(1L, guestNameVO);
            PeriodVO periodVO = new PeriodVO(LocalDate.now(), LocalDate.now().plusDays(10));
            BookingStatusVO bookingStatusVO = new BookingStatusVO(BookingStatusEnum.CANCELED.getId(), BookingStatusEnum.CANCELED.getDescription());

            new Booking(1L, guest, null, periodVO, bookingStatusVO);
        });

        assertEquals("Property is required.", exception.getMessage());
    }

    @Test
    void shouldThrowsExceptionIfCreateDomainObjectWithoutStatus() {
        DomainObjectValidationException exception = assertThrows(DomainObjectValidationException.class, () -> {
            GuestNameVO guestNameVO = new GuestNameVO("Jordan", "Love");
            Property property = new Property(1L, "Beach House");
            Guest guest = new Guest(1L, guestNameVO);
            PeriodVO periodVO = new PeriodVO(LocalDate.now(), LocalDate.now().plusDays(10));

            new Booking(1L, guest, property, periodVO, null);
        });

        assertEquals("Status is required.", exception.getMessage());
    }

    @Test
    void shouldThrowsExceptionIfCreateDomainObjectWithoutRequiredFields() {
        assertThrows(DomainObjectValidationException.class, () -> {
            new Booking(null, null, null, null);
        });
    }

    @Test
    void shouldCreateDomainObjectWithRequiredFields() {
        GuestNameVO guestNameVO = new GuestNameVO("Jordan", "Love");
        Guest guest = new Guest(1L, guestNameVO);
        Property property = new Property(1L, "Beach House");
        PeriodVO periodVO = new PeriodVO(LocalDate.now(), LocalDate.now().plusDays(10));
        BookingStatusVO bookingStatusVO = new BookingStatusVO(BookingStatusEnum.CANCELED.getId(), BookingStatusEnum.CANCELED.getDescription());

        new Booking(1L, guest, property, periodVO, bookingStatusVO);

        assertDoesNotThrow(() -> DomainObjectValidationException.class);
    }
}