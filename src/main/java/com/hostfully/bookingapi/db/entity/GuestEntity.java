package com.hostfully.bookingapi.db.entity;

import com.hostfully.bookingapi.db.entity.vo.GuestName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GUEST")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(nullable = false)
    private GuestName fullName;
}
