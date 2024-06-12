package com.hostfully.bookingapi.web.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BlockingUpdateRequest(@NotNull LocalDate checkIn, @NotNull LocalDate checkOut) {}
