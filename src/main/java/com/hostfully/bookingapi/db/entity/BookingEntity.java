package com.hostfully.bookingapi.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BOOKING")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private GuestEntity guest;

    @ManyToOne
    private PropertyEntity property;

    @Embedded
    private Period period;

    @ManyToOne
    private BookingStatusEntity status;

}
