package com.hostfully.bookingapi.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BLOCKING")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlockingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private PropertyEntity property;

    @Embedded
    private Period period;
}
