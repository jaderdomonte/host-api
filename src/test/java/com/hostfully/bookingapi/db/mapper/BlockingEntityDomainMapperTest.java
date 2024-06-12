package com.hostfully.bookingapi.db.mapper;

import com.hostfully.bookingapi.db.entity.BlockingEntity;
import com.hostfully.bookingapi.db.entity.Period;
import com.hostfully.bookingapi.db.entity.PropertyEntity;
import com.hostfully.bookingapi.domain.Blocking;
import com.hostfully.bookingapi.domain.PeriodVO;
import com.hostfully.bookingapi.domain.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BlockingEntityDomainMapperTest {

    @InjectMocks
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
    void shouldMaptoDomain() {
        Blocking blocking = mapper.toDomain(entity);

        assertEquals(entity.getId(), blocking.getId());
        assertEquals(entity.getProperty().getId(), blocking.getProperty().getId());
        assertEquals(entity.getProperty().getName(), blocking.getProperty().getName());
        assertEquals(entity.getPeriod().getCheckIn(), blocking.getPeriod().getCheckIn());
        assertEquals(entity.getPeriod().getCheckOut(), blocking.getPeriod().getCheckOut());
    }

    @Test
    void shouldMaptoEntity() {
        BlockingEntity blocking = mapper.toEntity(domain);

        assertEquals(blocking.getId(), domain.getId());
        assertEquals(blocking.getProperty().getId(), domain.getProperty().getId());
        assertEquals(blocking.getProperty().getName(), domain.getProperty().getName());
        assertEquals(blocking.getPeriod().getCheckIn(), domain.getPeriod().getCheckIn());
        assertEquals(blocking.getPeriod().getCheckOut(), domain.getPeriod().getCheckOut());
    }
}