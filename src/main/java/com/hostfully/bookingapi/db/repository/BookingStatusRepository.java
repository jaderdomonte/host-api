package com.hostfully.bookingapi.db.repository;

import com.hostfully.bookingapi.db.entity.BookingStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingStatusRepository extends JpaRepository<BookingStatusEntity, Long> {
}
