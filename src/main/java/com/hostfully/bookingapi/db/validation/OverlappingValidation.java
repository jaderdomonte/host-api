package com.hostfully.bookingapi.db.validation;

import com.hostfully.bookingapi.db.entity.Period;
import com.hostfully.bookingapi.db.repository.BlockingRepository;
import com.hostfully.bookingapi.db.repository.BookingRepository;
import com.hostfully.bookingapi.exceptions.PeriodOverlappingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OverlappingValidation {

    private final BookingRepository bookingRepository;

    private final BlockingRepository blockingRepository;

    public void checkOverlappingBlocking(Long propertyId, Period period) {
        int overlappingBlockingsCount = blockingRepository.checkOverlapping(propertyId, period);

        checkOverlappingCount(overlappingBlockingsCount, "This Property is already blocked in this period!");
    }

    public void checkOverlappingBlocking(Long id, Long propertyId, Period period) {
        int overlappingBlockingsCount = blockingRepository.checkOverlapping(id, propertyId, period);

        checkOverlappingCount(overlappingBlockingsCount, "This Property is already blocked in this period!");
    }

    public void checkOverlappingBooking(Long propertyId, Period period) {
        int overlappingBookingCount = bookingRepository.checkOverlapping(propertyId, period);

        checkOverlappingCount(overlappingBookingCount, "This Property is already booked in this period!");
    }

    public void checkOverlappingBooking(Long id, Long propertyId, Period period) {
        int overlappingBookingCount = bookingRepository.checkOverlapping(id, propertyId, period);

        checkOverlappingCount(overlappingBookingCount, "This Property is already booked in this period!");
    }

    private void checkOverlappingCount(int overlappingCount, String s) {
        if (overlappingCount > 0) {
            throw new PeriodOverlappingException(s);
        }
    }
}
