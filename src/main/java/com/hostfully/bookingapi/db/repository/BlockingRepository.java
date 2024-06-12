package com.hostfully.bookingapi.db.repository;

import com.hostfully.bookingapi.db.entity.BlockingEntity;
import com.hostfully.bookingapi.db.entity.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlockingRepository extends JpaRepository<BlockingEntity, Long> {

    @Query("FROM BlockingEntity b " +
            "WHERE (:propertyId IS NULL OR b.property.id = :propertyId) " +
            "ORDER BY b.property.id, b.period.checkIn")
    List<BlockingEntity> findByFilter(@Param("propertyId") Long propertyId);

    @Query("SELECT COUNT(b.id) " +
            "FROM BlockingEntity b " +
            "WHERE (" +
            "(b.period.checkIn <= :#{#period.checkIn} AND b.period.checkOut > :#{#period.checkIn}) OR " +
            "(b.period.checkIn < :#{#period.checkOut} AND b.period.checkOut >= :#{#period.checkOut}) OR" +
            "(b.period.checkIn >= :#{#period.checkIn} AND b.period.checkOut <= :#{#period.checkOut})" +
            ") " +
            "AND b.property.id = :propertyId")
    int checkOverlapping(@Param("propertyId") Long propertyId, @Param("period") Period period);

    @Query("SELECT COUNT(b.id) " +
            "FROM BlockingEntity b " +
            "WHERE (" +
            "(b.period.checkIn <= :#{#period.checkIn} AND b.period.checkOut > :#{#period.checkIn}) OR " +
            "(b.period.checkIn < :#{#period.checkOut} AND b.period.checkOut >= :#{#period.checkOut}) OR" +
            "(b.period.checkIn >= :#{#period.checkIn} AND b.period.checkOut <= :#{#period.checkOut})" +
            ") " +
            "AND b.property.id = :propertyId " +
            "AND b.id <> :id ")
    int checkOverlapping(@Param("id") Long id, @Param("propertyId") Long propertyId, @Param("period") Period period);
}
