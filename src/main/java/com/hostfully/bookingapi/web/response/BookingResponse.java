package com.hostfully.bookingapi.web.response;

import com.hostfully.bookingapi.web.dto.GuestDto;
import com.hostfully.bookingapi.web.dto.PeriodDto;
import com.hostfully.bookingapi.web.dto.PropertyDto;

public record BookingResponse(Long id, GuestDto guest, PropertyDto property, PeriodDto period, String status) {
}
