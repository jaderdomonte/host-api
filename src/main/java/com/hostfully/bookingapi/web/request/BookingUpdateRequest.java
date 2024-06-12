package com.hostfully.bookingapi.web.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookingUpdateRequest(@NotNull Long guestId,
                                   @NotNull LocalDate checkIn,
                                   @NotNull LocalDate checkOut) {}
