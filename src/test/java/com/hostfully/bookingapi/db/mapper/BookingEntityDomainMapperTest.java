package com.hostfully.bookingapi.db.mapper;

import com.hostfully.bookingapi.db.entity.*;
import com.hostfully.bookingapi.db.enumeration.BookingStatusEnum;
import com.hostfully.bookingapi.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BookingEntityDomainMapperTest {

    @InjectMocks
    private BookingEntityDomainMapper mapper;

    private Booking domain;

    private BookingEntity entity;

    @BeforeEach
    void setUp(){
        GuestNameVO guestNameVO = new GuestNameVO("Jordan", "Love");
        Guest guest = new Guest(1L, guestNameVO);
        Property property = new Property(1L, "Beach House");
        PeriodVO periodVO = new PeriodVO(LocalDate.now(), LocalDate.now().plusDays(10));
        BookingStatusVO bookingStatusVO = new BookingStatusVO(BookingStatusEnum.CONFIRMED.getId(), BookingStatusEnum.CONFIRMED.getDescription());

        domain = new Booking(1L, guest, property, periodVO, bookingStatusVO);

        GuestEntity guestEntity = GuestEntity.builder().id(domain.getGuest().getId()).fullName(GuestName.builder().firstName("Brock").lastName("Purdy").build()) .build();
        Period periodEntity = Period.builder().checkIn(domain.getPeriod().getCheckIn()).checkOut(domain.getPeriod().getCheckOut()).build();
        BookingStatusEntity bookingStatusEntity = BookingStatusEntity.builder().id(BookingStatusEnum.CONFIRMED.getId()).description(BookingStatusEnum.CONFIRMED.getDescription()).build();
        PropertyEntity propertyEntity = PropertyEntity.builder().id(domain.getProperty().getId()).name(domain.getProperty().getName()).build();

        entity = BookingEntity.builder().id(1L).guest(guestEntity).property(propertyEntity).period(periodEntity).status(bookingStatusEntity).build();
    }

    @Test
    void shouldMaptoDomain() {
        Booking booking = mapper.toDomain(entity);

        assertEquals(entity.getId(), booking.getId());
        assertEquals(entity.getGuest().getId(), booking.getGuest().getId());
        assertEquals(entity.getGuest().getFullName().getFirstName(), booking.getGuest().getFullName().getFirstName());
        assertEquals(entity.getGuest().getFullName().getLastName(), booking.getGuest().getFullName().getLastName());
        assertEquals(entity.getProperty().getId(), booking.getProperty().getId());
        assertEquals(entity.getProperty().getName(), booking.getProperty().getName());
        assertEquals(entity.getPeriod().getCheckIn(), booking.getPeriod().getCheckIn());
        assertEquals(entity.getPeriod().getCheckOut(), booking.getPeriod().getCheckOut());
        assertEquals(entity.getStatus().getId(), booking.getStatus().getId());
        assertEquals(entity.getStatus().getDescription(), booking.getStatus().getDescription());
    }

    @Test
    void shouldMaptoEntity() {
        BookingEntity booking = mapper.toEntity(domain);

        assertEquals(domain.getGuest().getId(), booking.getGuest().getId());
        assertEquals(domain.getProperty().getId(), booking.getProperty().getId());
        assertEquals(domain.getProperty().getName(), booking.getProperty().getName());
        assertEquals(domain.getPeriod().getCheckIn(), booking.getPeriod().getCheckIn());
        assertEquals(domain.getPeriod().getCheckOut(), booking.getPeriod().getCheckOut());
        assertEquals(domain.getStatus().getId(), booking.getStatus().getId());
    }
}