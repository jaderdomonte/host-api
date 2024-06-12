package com.hostfully.bookingapi.db.enumeration;

import lombok.Getter;

@Getter
public enum BookingStatus {
    CONFIRMED(1L, "CONFIRMED"),
    CANCELED(2L,"CANCELED");

    private Long id;

    private String description;

    private BookingStatus(Long id, String description) {
        this.id = id;
        this.description = description;
    }
}
