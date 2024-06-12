package com.hostfully.bookingapi.web.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BlockingCreateRequest (@NotNull Long propertyId,
                                     @NotNull LocalDate checkIn,
                                     @NotNull LocalDate checkOut) {
}
