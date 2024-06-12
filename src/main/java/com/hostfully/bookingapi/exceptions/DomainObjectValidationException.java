package com.hostfully.bookingapi.exceptions;

public class DomainObjectValidationException extends RuntimeException {

    public DomainObjectValidationException(String message) {
        super(message);
    }
}
