package com.hostfully.bookingapi.db.mapper;

import com.hostfully.bookingapi.db.entity.*;
import com.hostfully.bookingapi.db.enumeration.BookingStatusEnum;
import com.hostfully.bookingapi.domain.*;
import org.springframework.stereotype.Component;

@Component
public class BookingEntityDomainMapper {

    public BookingEntity toEntity(Booking domain){
        GuestEntity guest = GuestEntity.builder().id(domain.getGuest().getId()).build();
        Period period = Period.builder().checkIn(domain.getPeriod().getCheckIn()).checkOut(domain.getPeriod().getCheckOut()).build();
        BookingStatusEntity bookingStatusEntity = BookingStatusEntity.builder().id(BookingStatusEnum.CONFIRMED.getId()).build();
        PropertyEntity propertyEntity = PropertyEntity.builder().id(domain.getProperty().getId()).name(domain.getProperty().getName()).build();

        return BookingEntity.builder().guest(guest).property(propertyEntity).period(period).status(bookingStatusEntity).build();
    }

    public Booking toDomain(BookingEntity entity){
        GuestNameVO guestNameVO = new GuestNameVO(entity.getGuest().getFullName().getFirstName(), entity.getGuest().getFullName().getLastName());
        Guest guest = new Guest(entity.getGuest().getId(), guestNameVO);
        Property property = new Property(entity.getProperty().getId(), entity.getProperty().getName());
        PeriodVO periodVO = new PeriodVO(entity.getPeriod().getCheckIn(), entity.getPeriod().getCheckOut());
        BookingStatusVO bookingStatusVO = new BookingStatusVO(entity.getStatus().getId(), entity.getStatus().getDescription());

        return new Booking(entity.getId(), guest, property, periodVO, bookingStatusVO);
    }
}
