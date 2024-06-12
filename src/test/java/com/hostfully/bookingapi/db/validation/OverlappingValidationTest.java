package com.hostfully.bookingapi.db.validation;

import com.hostfully.bookingapi.db.entity.Period;
import com.hostfully.bookingapi.db.repository.BlockingRepository;
import com.hostfully.bookingapi.db.repository.BookingRepository;
import com.hostfully.bookingapi.exceptions.PeriodOverlappingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OverlappingValidationTest {

    @InjectMocks
    private OverlappingValidation validation;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BlockingRepository blockingRepository;

    private Period period;

    @BeforeEach
    void setUp(){
        period = Period.builder().checkIn(LocalDate.now()).checkOut(LocalDate.now().plusDays(10)).build();
    }

    @Test
    void shouldCheckOverlappingBlockingSucessWithoutBlockingId(){
        when(blockingRepository.checkOverlapping(any(), any())).thenReturn(0);

        validation.checkOverlappingBlocking(1L, period);

        assertDoesNotThrow(() -> PeriodOverlappingException.class);
        verify(blockingRepository).checkOverlapping(any(), any());
    }

    @Test
    void shouldThrowExceptionWhenThereIsBlockingOverlappingWithoutBlockingId(){
        when(blockingRepository.checkOverlapping(any(), any())).thenReturn(1);

        Assertions.assertThrows(PeriodOverlappingException.class, () ->{
            validation.checkOverlappingBlocking(1L, period);
        });

        verify(blockingRepository).checkOverlapping(any(), any());
    }

    @Test
    void shouldCheckOverlappingBlockingSucessWithBlockingId(){
        when(blockingRepository.checkOverlapping(any(), any(), any())).thenReturn(0);

        validation.checkOverlappingBlocking(1L, 1L, period);

        assertDoesNotThrow(() -> PeriodOverlappingException.class);
        verify(blockingRepository).checkOverlapping(any(), any(), any());
    }

    @Test
    void shouldThrowExceptionWhenThereIsBlockingOverlappingWithBlockingId(){
        when(blockingRepository.checkOverlapping(any(), any(), any())).thenReturn(1);

        Assertions.assertThrows(PeriodOverlappingException.class, () ->{
            validation.checkOverlappingBlocking(1L, 1L, period);
        });

        verify(blockingRepository).checkOverlapping(any(), any(), any());
    }

    @Test
    void shouldCheckOverlappingBookingSucessWithBookingId(){
        when(bookingRepository.checkOverlapping(any(), any(), any())).thenReturn(1);

        Assertions.assertThrows(PeriodOverlappingException.class, () ->{
            validation.checkOverlappingBooking(1L, 1L, period);
        });

        verify(bookingRepository).checkOverlapping(any(), any(), any());
    }

    @Test
    void shouldThrowExceptionWhenThereIsBookingOverlappingWithBookingId(){
        when(bookingRepository.checkOverlapping(any(), any(), any())).thenReturn(1);

        Assertions.assertThrows(PeriodOverlappingException.class, () ->{
            validation.checkOverlappingBooking(1L, 1L, period);
        });

        verify(bookingRepository).checkOverlapping(any(), any(), any());
    }

    @Test
    void shouldCheckOverlappingBookingSucessWithoutBookingId(){
        when(bookingRepository.checkOverlapping(any(), any())).thenReturn(1);

        Assertions.assertThrows(PeriodOverlappingException.class, () ->{
            validation.checkOverlappingBooking(1L, period);
        });

        verify(bookingRepository).checkOverlapping(any(), any());
    }

    @Test
    void shouldThrowExceptionWhenThereIsBookingOverlappingWithoutBookingId(){
        when(bookingRepository.checkOverlapping(any(), any())).thenReturn(1);

        Assertions.assertThrows(PeriodOverlappingException.class, () ->{
            validation.checkOverlappingBooking(1L, period);
        });

        verify(bookingRepository).checkOverlapping(any(), any());
    }
}