package com.hostfully.bookingapi.domain;

import com.hostfully.bookingapi.domain.validation.DomainValidation;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class BookingStatusVO implements DomainValidation {

    private Long id;

    private String description;

    public BookingStatusVO(String description){
        this.description = description;
        validate();
    }

    public BookingStatusVO(Long id, String description) {
        this(description);
        this.id = id;
    }

    public void validate(){
        validateField(StringUtils.isEmpty(description), "Description is required.");
    }
}
