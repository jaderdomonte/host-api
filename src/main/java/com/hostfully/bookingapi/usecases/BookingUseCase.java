package com.hostfully.bookingapi.usecases;

import com.hostfully.bookingapi.db.entity.BookingEntity;
import com.hostfully.bookingapi.db.entity.GuestEntity;
import com.hostfully.bookingapi.db.enumeration.BookingStatusEnum;
import com.hostfully.bookingapi.db.mapper.BookingEntityDomainMapper;
import com.hostfully.bookingapi.db.repository.BookingRepository;
import com.hostfully.bookingapi.db.repository.GuestRepository;
import com.hostfully.bookingapi.db.repository.PropertyRepository;
import com.hostfully.bookingapi.db.validation.OverlappingValidation;
import com.hostfully.bookingapi.domain.Booking;
import com.hostfully.bookingapi.exceptions.ResourceNotFoundException;
import com.hostfully.bookingapi.web.request.BookingFilter;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
public class BookingUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(BookingUseCase.class);

    private final BookingRepository bookingRepository;

    private final GuestRepository guestRepository;

    private final PropertyRepository propertyRepository;

    private final OverlappingValidation overlappingValidation;

    private final BookingEntityDomainMapper bookingEntityDomainMapper;

    public Booking getBookingById(Long id){
        LOG.info("Starting getting Booking {}", id);
        BookingEntity entity = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no Booking with id " + id));
        LOG.info("Returned Booking {}", id);
        return bookingEntityDomainMapper.toDomain(entity);
    }

    public List<Booking> getBookingsByFilter(BookingFilter filter){
        LOG.info("Starting getting Bookings by filter");
        List<BookingEntity> byFilter = bookingRepository.findByFilter(filter);
        LOG.info("Returned {} Bookings", byFilter.size());
        return byFilter.stream().map(booking -> bookingEntityDomainMapper.toDomain(booking)).toList();
    }

    public void createBooking(Booking domain){
        LOG.info("Starting creating Booking");
        BookingEntity entity = bookingEntityDomainMapper.toEntity(domain);

        guestRepository.findById(entity.getGuest().getId()).orElseThrow(() -> new ResourceNotFoundException("There is no  Guest with id " + entity.getGuest().getId()));
        propertyRepository.findById(entity.getProperty().getId()).orElseThrow(() -> new ResourceNotFoundException("There is no Property with id: " + entity.getProperty().getId()));

        overlappingValidation.checkOverlappingBooking(entity.getProperty().getId(), entity.getPeriod());
        overlappingValidation.checkOverlappingBlocking(entity.getProperty().getId(), entity.getPeriod());

        bookingRepository.save(entity);
        LOG.info("Booking created");
    }

    public void deleteBooking(Long id){
        LOG.info("Starting deleting Booking {}", id);
        BookingEntity bookingEntity = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no Booking with id " + id));
        bookingRepository.delete(bookingEntity);
        LOG.info("Booking {} deleted", id);
    }

    public void updateBooking(Long id, Booking booking){
        LOG.info("Starting updating Booking {}", id);
        BookingEntity entity = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no Booking with id " + id));
        GuestEntity guestEntity = guestRepository.findById(booking.getGuest().getId()).orElseThrow(() -> new ResourceNotFoundException("There is no  Guest with id " + booking.getGuest().getId()));

        entity.getPeriod().setCheckIn(booking.getPeriod().getCheckIn());
        entity.getPeriod().setCheckOut(booking.getPeriod().getCheckOut());
        entity.setGuest(guestEntity);

        overlappingValidation.checkOverlappingBooking(entity.getId(), entity.getProperty().getId(), entity.getPeriod());
        overlappingValidation.checkOverlappingBlocking(entity.getProperty().getId(), entity.getPeriod());

        bookingRepository.save(entity);
        LOG.info("Booking {} updated", id);
    }

    @Transactional
    public void cancelBooking(Long id){
        LOG.info("Starting cancelling Booking {}", id);
        bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no Booking with id " + id));
        bookingRepository.changeBookingStatus(id, BookingStatusEnum.CANCELED.getId());
        LOG.info("Booking {} canceled", id);
    }

    @Transactional
    public void activateBooking(Long id){
        LOG.info("Starting activating Booking {}", id);
        BookingEntity entity = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no Booking with id " + id));

        overlappingValidation.checkOverlappingBooking(entity.getId(), entity.getProperty().getId(), entity.getPeriod());
        overlappingValidation.checkOverlappingBlocking(entity.getProperty().getId(), entity.getPeriod());

        bookingRepository.changeBookingStatus(id, BookingStatusEnum.CONFIRMED.getId());
        LOG.info("Booking {} activated", id);
    }
}
