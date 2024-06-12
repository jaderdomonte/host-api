package com.hostfully.bookingapi.web.mapper;

import com.hostfully.bookingapi.domain.Blocking;
import com.hostfully.bookingapi.domain.PeriodVO;
import com.hostfully.bookingapi.domain.Property;
import com.hostfully.bookingapi.web.dto.PeriodDto;
import com.hostfully.bookingapi.web.dto.PropertyDto;
import com.hostfully.bookingapi.web.request.BlockingCreateRequest;
import com.hostfully.bookingapi.web.request.BlockingUpdateRequest;
import com.hostfully.bookingapi.web.response.BlockingResponse;
import org.springframework.stereotype.Component;

@Component
public class BlockingDtoDomainMapper {

    public BlockingResponse fromDomainToResponse(Blocking domain){
        PropertyDto property = new PropertyDto(domain.getProperty().getId(), domain.getProperty().getName());
        PeriodDto periodDto = new PeriodDto(domain.getPeriod().getCheckIn(), domain.getPeriod().getCheckOut());
        return new BlockingResponse(domain.getId(), property, periodDto);
    }

    public Blocking fromRequestToDomain(BlockingCreateRequest request){
        Property property = new Property(request.propertyId());
        PeriodVO periodVO = new PeriodVO(request.checkIn(), request.checkOut());
        return new Blocking(property, periodVO);
    }

    public PeriodDto fromRequestToDomain(BlockingUpdateRequest request){
        return new PeriodDto(request.checkIn(), request.checkOut());
    }
}
