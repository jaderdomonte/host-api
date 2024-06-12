package com.hostfully.bookingapi.web.dto;

import java.time.LocalDate;

public record PeriodDto(LocalDate checkIn, LocalDate checkOut){
}
