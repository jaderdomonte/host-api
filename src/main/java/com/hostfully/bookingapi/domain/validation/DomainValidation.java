package com.hostfully.bookingapi.domain.validation;

import com.hostfully.bookingapi.exceptions.DomainObjectValidationException;

public interface DomainValidation {

    void validate();

    default void validateField(boolean condition, String s) {
        if (condition) {
            throw new DomainObjectValidationException(s);
        }
    }
}
