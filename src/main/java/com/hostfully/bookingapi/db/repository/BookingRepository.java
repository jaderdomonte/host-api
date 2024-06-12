package com.hostfully.bookingapi.db.repository;

import com.hostfully.bookingapi.db.entity.BookingEntity;
import com.hostfully.bookingapi.db.entity.Period;
import com.hostfully.bookingapi.web.request.BookingFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    Optional<BookingEntity> findByIdAndStatusId(Long id, Long statusId);

    @Query("FROM BookingEntity b " +
            "WHERE (:#{#filter.propertyId} IS NULL OR b.property.id = :#{#filter.propertyId}) " +
            "AND (:#{#filter.guestId} IS NULL OR b.guest.id = :#{#filter.guestId}) " +
            "ORDER BY b.property.id, b.period.checkIn")
    List<BookingEntity> findByFilter(@Param("filter") BookingFilter filter);

    @Modifying
    @Query("UPDATE BookingEntity b SET b.status.id = :statusId WHERE b.id = :id")
    void changeBookingStatus(@Param("id") Long id, @Param("statusId") Long statusId);

    @Query("SELECT COUNT(b.id) " +
            "FROM BookingEntity b " +
            "WHERE ( " +
            "(b.period.checkIn <= :#{#period.checkIn} AND b.period.checkOut > :#{#period.checkIn}) OR " +
            "(b.period.checkIn < :#{#period.checkOut} AND b.period.checkOut >= :#{#period.checkOut}) OR " +
            "(b.period.checkIn >= :#{#period.checkIn} AND b.period.checkOut <= :#{#period.checkOut})" +
            ") " +
            "AND b.status.id = 1 " +
            "AND b.property.id = :propertyId")
    int checkOverlapping(@Param("propertyId") Long propertyId, @Param("period") Period period);

    @Query("SELECT COUNT(b.id) " +
            "FROM BookingEntity b " +
            "WHERE ( " +
            "(b.period.checkIn <= :#{#period.checkIn} AND b.period.checkOut > :#{#period.checkIn}) OR " +
            "(b.period.checkIn < :#{#period.checkOut} AND b.period.checkOut >= :#{#period.checkOut}) OR " +
            "(b.period.checkIn >= :#{#period.checkIn} AND b.period.checkOut <= :#{#period.checkOut})" +
            ") " +
            "AND b.status.id = 1 " +
            "AND b.property.id = :propertyId " +
            "AND b.id <> :id")
    int checkOverlapping(@Param("id") Long id, @Param("propertyId") Long propertyId, @Param("period") Period period);

}
