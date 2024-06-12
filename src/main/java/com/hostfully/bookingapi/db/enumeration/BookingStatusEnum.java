package com.hostfully.bookingapi.db.enumeration;

import lombok.Getter;

@Getter
public enum BookingStatusEnum {
    CONFIRMED(1L, "CONFIRMED"),
    CANCELED(2L,"CANCELED");

    private Long id;

    private String description;

    private BookingStatusEnum(Long id, String description) {
        this.id = id;
        this.description = description;
    }
}
