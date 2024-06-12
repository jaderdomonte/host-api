package com.hostfully.bookingapi.db.repository;

import com.hostfully.bookingapi.db.entity.GuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<GuestEntity, Long> {
}
