package com.hostfully.bookingapi.exceptions;

public class PeriodOverlappingException extends RuntimeException {

    public PeriodOverlappingException() {
        super();
    }

    public PeriodOverlappingException(String message) {
        super(message);
    }
}
