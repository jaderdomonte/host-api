package com.hostfully.bookingapi.web.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookingCreateRequest(@NotNull Long propertyId,
                                   @NotNull Long guestId,
                                   @NotNull LocalDate checkIn,
                                   @NotNull LocalDate checkOut) {
}
