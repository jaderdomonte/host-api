package com.hostfully.bookingapi.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BOOKING_STATUS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingStatusEntity {

    @Id
    private Long id;

    private String description;

    public BookingStatusEntity(Long id) {
        this.id = id;
    }
}
