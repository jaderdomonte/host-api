package com.hostfully.bookingapi.domain;

import com.hostfully.bookingapi.domain.validation.DomainValidation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Guest implements DomainValidation {

    private Long id;

    private GuestNameVO fullName;

    public Guest(Long id) {
        this.id = id;
    }

    public Guest(Long id, GuestNameVO fullName) {
        this.id = id;
        this.fullName = fullName;
        validate();
    }

    public void validate(){
        validateField(id == null && fullName == null, "Guest fullName is required.");
    }
}
