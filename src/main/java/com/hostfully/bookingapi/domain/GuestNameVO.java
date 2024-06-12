package com.hostfully.bookingapi.domain;

import com.hostfully.bookingapi.domain.validation.DomainValidation;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class GuestNameVO implements DomainValidation {

    private String firstName;

    private String lastName;

    public GuestNameVO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        validate();
    }

    public void validate(){
        validateField(StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName), "Guest firstName and lastName are required.");
    }
}
