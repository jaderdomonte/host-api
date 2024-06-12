package com.hostfully.bookingapi.web.mapper;

import com.hostfully.bookingapi.domain.Blocking;
import com.hostfully.bookingapi.domain.PeriodVO;
import com.hostfully.bookingapi.domain.Property;
import com.hostfully.bookingapi.web.dto.PeriodDto;
import com.hostfully.bookingapi.web.request.BlockingCreateRequest;
import com.hostfully.bookingapi.web.request.BlockingUpdateRequest;
import com.hostfully.bookingapi.web.response.BlockingResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
class BlockingDtoDomainMapperTest {

    @InjectMocks
    private BlockingDtoDomainMapper mapper;

    private Blocking domain;

    @BeforeEach
    void setUp(){
        Property property = new Property(1L, "Beach House");
        PeriodVO periodVO = new PeriodVO(LocalDate.now(), LocalDate.now().plusDays(10));
        domain = new Blocking(property, periodVO);
    }

    @Test
    void shouldMapFromDomainToResponse() {
        BlockingResponse response = mapper.fromDomainToResponse(domain);

        assertEquals(domain.getId(), response.id());
        assertEquals(domain.getProperty().getId(), response.property().id());
        assertEquals(domain.getProperty().getName(), response.property().name());
        assertEquals(domain.getPeriod().getCheckIn(), response.period().checkIn());
        assertEquals(domain.getPeriod().getCheckOut(), response.period().checkOut());
    }

    @Test
    void shouldMapFromCreateRequestToDomain() {
        BlockingCreateRequest request = new BlockingCreateRequest(1L, LocalDate.now(), LocalDate.now().plusDays(10));

        Blocking blocking = mapper.fromRequestToDomain(request);

        assertEquals(request.propertyId(), blocking.getProperty().getId());
        assertEquals(request.checkIn(), blocking.getPeriod().getCheckIn());
        assertEquals(request.checkOut(), blocking.getPeriod().getCheckOut());
    }

    @Test
    void shouldMapFromUpdateRequestToDomain() {
        BlockingUpdateRequest request = new BlockingUpdateRequest(LocalDate.now(), LocalDate.now().plusDays(10));

        PeriodDto periodDto = mapper.fromRequestToDomain(request);

        assertEquals(request.checkIn(), periodDto.checkIn());
        assertEquals(request.checkOut(), periodDto.checkOut());
    }
}