package com.hostfully.bookingapi.domain;

import com.hostfully.bookingapi.domain.validation.DomainValidation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Booking implements DomainValidation {

    private Long id;

    private Guest guest;

    private PeriodVO period;

    private Property property;

    private BookingStatusVO status;

    public Booking(Long id, Guest guest, Property property, PeriodVO period, BookingStatusVO status) {
        this.id = id;
        this.guest = guest;
        this.property = property;
        this.period = period;
        this.status = status;
        validate();
    }

    public Booking(Guest guest, Property property, PeriodVO period, BookingStatusVO status) {
        this.guest = guest;
        this.property = property;
        this.period = period;
        this.status = status;
        validate();
    }

    public Booking(Guest guest, PeriodVO period) {
        this.guest = guest;
        this.period = period;
        validateGuestAndPeriod();
    }

    private void validateGuestAndPeriod(){
        validateField(guest == null, "Guest is required.");
        validateField(period == null, "Period is required.");
    }

    private void validatePropertyAndStatus(){
        validateField(property == null, "Property is required.");
        validateField(status == null, "Status is required.");
    }

    public void validate(){
        validatePropertyAndStatus();
        validateGuestAndPeriod();
    }
}
