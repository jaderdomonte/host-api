package com.hostfully.bookingapi.db.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestName {

    private String firstName;

    private String lastName;
}
