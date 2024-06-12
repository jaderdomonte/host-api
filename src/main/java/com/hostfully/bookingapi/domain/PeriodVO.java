package com.hostfully.bookingapi.domain;

import com.hostfully.bookingapi.domain.validation.DomainValidation;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PeriodVO implements DomainValidation {

    private LocalDate checkIn;

    private LocalDate checkOut;

    public PeriodVO(LocalDate checkIn, LocalDate checkOut) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        validate();
    }

    public void validate(){
        validateField(checkIn == null || checkOut == null, "CheckIn and CheckOut are required.");
        validateField(checkIn.isBefore(LocalDate.now()), "CheckIn date should be equals or greater than today.");
        validateField(checkOut.isBefore(LocalDate.now()), "CheckOut date should greater than today.");
        validateField(checkIn.isEqual(checkOut) || checkIn.isAfter(checkOut), "CheckOut date should be greater than CheckIn date.");
    }
}
