package com.hostfully.bookingapi.web.response;

import com.hostfully.bookingapi.web.dto.PeriodDto;
import com.hostfully.bookingapi.web.dto.PropertyDto;

public record BlockingResponse(Long id, PropertyDto property, PeriodDto period) {
}
