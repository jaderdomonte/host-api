package com.hostfully.bookingapi.usecases;

import com.hostfully.bookingapi.db.entity.BlockingEntity;
import com.hostfully.bookingapi.db.entity.Period;
import com.hostfully.bookingapi.db.entity.PropertyEntity;
import com.hostfully.bookingapi.db.mapper.BlockingEntityDomainMapper;
import com.hostfully.bookingapi.db.repository.BlockingRepository;
import com.hostfully.bookingapi.db.repository.PropertyRepository;
import com.hostfully.bookingapi.db.validation.OverlappingValidation;
import com.hostfully.bookingapi.domain.Blocking;
import com.hostfully.bookingapi.domain.PeriodVO;
import com.hostfully.bookingapi.domain.Property;
import com.hostfully.bookingapi.exceptions.PeriodOverlappingException;
import com.hostfully.bookingapi.exceptions.ResourceNotFoundException;
import com.hostfully.bookingapi.web.dto.PeriodDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlockingUseCaseTest {

    @InjectMocks
    private BlockingUseCase useCase;

    @Mock
    private BlockingRepository blockingRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private OverlappingValidation overlappingValidation;

    @Mock
    private BlockingEntityDomainMapper mapper;

    private Blocking domain;

    private BlockingEntity entity;

    @BeforeEach
    void setUp(){
        Property property = new Property(1L, "Beach House");
        PeriodVO periodVO = new PeriodVO(LocalDate.now(), LocalDate.now().plusDays(10));
        domain = new Blocking(property, periodVO);

        Period periodEntity = Period.builder().checkIn(domain.getPeriod().getCheckIn()).checkOut(domain.getPeriod().getCheckOut()).build();
        PropertyEntity propertyEntity = PropertyEntity.builder().id(domain.getProperty().getId()).name(domain.getProperty().getName()).build();
        entity = BlockingEntity.builder().property(propertyEntity).period(periodEntity).build();
    }

    @Test
    void shouldReturnBlockingsByFilter(){
        when(blockingRepository.findByFilter(any())).thenReturn(asList(BlockingEntity.builder().build()));
        when(mapper.toDomain(any())).thenReturn(domain);

        List<Blocking> allBlockings = useCase.getBlockingsByFilter(1L);

        Assertions.assertEquals(1, allBlockings.size());
    }

    @Test
    void shouldCreateBlocking(){
        when(mapper.toEntity(any())).thenReturn(entity);
        doNothing().when(overlappingValidation).checkOverlappingBlocking(any(), any(), any());
        doNothing().when(overlappingValidation).checkOverlappingBooking(any(), any());
        when(blockingRepository.save(any())).thenReturn(BlockingEntity.builder().build());
        when(propertyRepository.findById(any())).thenReturn(Optional.of(PropertyEntity.builder().build()));

        useCase.createBlocking(domain);

        verify(overlappingValidation).checkOverlappingBlocking(any(), any(), any());
        verify(overlappingValidation).checkOverlappingBooking(any(), any());
        verify(blockingRepository).save(any());
        verify(propertyRepository).findById(any());
        verify(propertyRepository).findById(any());
    }

    @Test
    void shouldThrowExceptionWhenThereIsOverlappingPeriodOnCreate() {
        when(mapper.toEntity(any())).thenReturn(entity);
        when(propertyRepository.findById(any())).thenReturn(Optional.of(PropertyEntity.builder().build()));
        doThrow(new PeriodOverlappingException()).when(overlappingValidation).checkOverlappingBlocking(any(), any(), any());

        assertThrows(PeriodOverlappingException.class, () -> {
            useCase.createBlocking(domain);
        });

        verify(overlappingValidation, only()).checkOverlappingBlocking(any(), any(), any());
        verify(blockingRepository, never()).save(any());
        verify(propertyRepository).findById(any());
    }

    @Test
    void shouldDeleteBlocking(){
        when(blockingRepository.findById(any())).thenReturn(Optional.of(BlockingEntity.builder().build()));
        doNothing().when(blockingRepository).delete(any());

        useCase.deleteBlocking(1L);

        verify(blockingRepository).delete(any());
    }

    @Test
    void shouldThrowsExceptionWhenResourceDoesNotExistsOnDelete(){
        when(blockingRepository.findById(any())).thenThrow(new ResourceNotFoundException("Message"));

        assertThrows(ResourceNotFoundException.class, () ->{
            useCase.deleteBlocking(1L);
        });

        verify(blockingRepository, never()).delete(any());
    }

    @Test
    void shouldUpdateBlocking(){
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        PeriodDto periodDto = new PeriodDto(today, tomorrow);

        when(blockingRepository.save(any())).thenReturn(entity);
        doNothing().when(overlappingValidation).checkOverlappingBlocking(any(), any(), any());
        doNothing().when(overlappingValidation).checkOverlappingBooking(any(), any());
        when(blockingRepository.findById(any())).thenReturn(Optional.of(entity));

        useCase.updateBlocking(1L, periodDto);

        assertTrue(entity.getPeriod().getCheckIn().isEqual(periodDto.checkIn()));
        assertTrue(entity.getPeriod().getCheckOut().isEqual(periodDto.checkOut()));

        verify(overlappingValidation).checkOverlappingBlocking(any(), any(), any());
        verify(overlappingValidation).checkOverlappingBooking(any(), any());
        verify(blockingRepository).save(any());
        verify(blockingRepository).findById(any());
    }

    @Test
    void shouldThrowExceptionWhenThereIsOverlappingPeriodOnUpdate() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate nextWeek = LocalDate.now().plusDays(7);
        LocalDate nextWeekPlusOne = LocalDate.now().plusDays(8);

        PeriodDto periodDto = new PeriodDto(today, tomorrow);
        Period period = Period.builder().checkIn(nextWeek).checkOut(nextWeekPlusOne).build();
        PropertyEntity propertyEntity = PropertyEntity.builder().id(1L).build();
        BlockingEntity entity = BlockingEntity.builder().period(period).property(propertyEntity).build();

        when(blockingRepository.findById(any())).thenReturn(Optional.of(entity));
        doThrow(new PeriodOverlappingException()).when(overlappingValidation).checkOverlappingBlocking(any(), any(), any());

        assertThrows(PeriodOverlappingException.class, () -> {
            useCase.updateBlocking(1L, periodDto);
        });

        verify(overlappingValidation).checkOverlappingBlocking(any(), any(), any());
        verify(blockingRepository).findById(any());
        verify(blockingRepository, never()).save(any());
    }

    @Test
    void shouldThrowsExceptionWhenResourceDoesNotExistsOnUpdate(){
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        PeriodDto periodDto = new PeriodDto(today, tomorrow);

        when(blockingRepository.findById(any())).thenThrow(new ResourceNotFoundException("Message"));

        assertThrows(ResourceNotFoundException.class, () ->{
            useCase.updateBlocking(1L, periodDto);
        });

        verify(overlappingValidation, never()).checkOverlappingBlocking(any(), any(), any());
        verify(blockingRepository, never()).save(any());
    }
}