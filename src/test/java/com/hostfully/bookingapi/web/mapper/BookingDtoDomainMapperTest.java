package com.hostfully.bookingapi.web.mapper;

import com.hostfully.bookingapi.db.enumeration.BookingStatusEnum;
import com.hostfully.bookingapi.domain.*;
import com.hostfully.bookingapi.web.request.BookingCreateRequest;
import com.hostfully.bookingapi.web.request.BookingUpdateRequest;
import com.hostfully.bookingapi.web.response.BookingResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
class BookingDtoDomainMapperTest {

    @InjectMocks
    private BookingDtoDomainMapper mapper;

    private Booking domain;

    @BeforeEach
    void setUp(){
        GuestNameVO guestNameVO = new GuestNameVO("Jordan", "Love");
        Guest guest = new Guest(1L, guestNameVO);
        Property property = new Property(1L, "Beach House");
        PeriodVO periodVO = new PeriodVO(LocalDate.now(), LocalDate.now().plusDays(10));
        BookingStatusVO bookingStatusVO = new BookingStatusVO(BookingStatusEnum.CANCELED.getId(), BookingStatusEnum.CANCELED.getDescription());

        domain = new Booking(1L, guest, property, periodVO, bookingStatusVO);
    }

    @Test
    void shouldMapFromDomainToResponse() {
        BookingResponse response = mapper.fromDomainToResponse(domain);

        assertEquals(domain.getId(), response.id());
        assertEquals(domain.getGuest().getId(), response.guest().id());
        assertEquals(domain.getGuest().getFullName().getFirstName(), response.guest().firstName());
        assertEquals(domain.getGuest().getFullName().getLastName(), response.guest().lastName());
        assertEquals(domain.getPeriod().getCheckIn(), response.period().checkIn());
        assertEquals(domain.getPeriod().getCheckOut(), response.period().checkOut());
        assertEquals(domain.getProperty().getId(), response.property().id());
        assertEquals(domain.getProperty().getName(), response.property().name());
    }

    @Test
    void shouldMapFromCreateRequestToDomain() {
        BookingCreateRequest request = new BookingCreateRequest(1L, 1L, LocalDate.now(), LocalDate.now().plusDays(1));

        Booking booking = mapper.fromRequestToDomain(request);
        assertEquals(request.guestId(), booking.getGuest().getId());
        assertEquals(request.propertyId(), booking.getProperty().getId());
        assertEquals(request.checkIn(), booking.getPeriod().getCheckIn());
        assertEquals(request.checkOut(), booking.getPeriod().getCheckOut());
    }

    @Test
    void shouldMapFromUpdateRequestToDomain() {
        BookingUpdateRequest request = new BookingUpdateRequest(1L, LocalDate.now(), LocalDate.now().plusDays(1));

        Booking booking = mapper.fromRequestToDomain(request);
        assertEquals(request.guestId(), booking.getGuest().getId());
        assertEquals(request.checkIn(), booking.getPeriod().getCheckIn());
        assertEquals(request.checkOut(), booking.getPeriod().getCheckOut());
    }
}