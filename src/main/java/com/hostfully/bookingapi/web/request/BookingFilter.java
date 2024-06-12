package com.hostfully.bookingapi.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingFilter{
        private Long propertyId;
        private Long guestId;
}
