package com.hostfully.bookingapi.exceptions.handler;

import com.hostfully.bookingapi.exceptions.DomainObjectValidationException;
import com.hostfully.bookingapi.exceptions.PeriodOverlappingException;
import com.hostfully.bookingapi.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    ProblemDetail handlerResourceNotFoundException(ResourceNotFoundException exception){
        LOG.error("Finding resource error: {}", exception.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problemDetail.setTitle("Resource not found.");

        return problemDetail;
    }

    @ExceptionHandler(DateTimeParseException.class)
    ProblemDetail handlerDateTimeParseException(DateTimeParseException exception){
        LOG.error("Period invalid error: {}", exception.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setTitle("Some Date value is invalid.");

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        LOG.error("Validation fields error: {}", exception.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "There are some validation errors");
        problemDetail.setTitle("Bad Request");
        problemDetail.setProperty("Validation Errors", getErrors(exception));

        return problemDetail;
    }

    private Map<String, List<String>> getErrors(MethodArgumentNotValidException exception) {
        Map<String, List<String>> errors = exception.getBindingResult().getFieldErrors()
                                                        .stream()
                                                        .collect(Collectors.groupingBy(FieldError::getField,
                                                                Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));
        return errors;
    }

    @ExceptionHandler(PeriodOverlappingException.class)
    ProblemDetail handlerPeriodOverlappingException(PeriodOverlappingException exception){
        LOG.error("Period overllaping error: {}", exception.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setTitle("Bad Request");

        return problemDetail;
    }

    @ExceptionHandler(DomainObjectValidationException.class)
    ProblemDetail handlerDomainObjectValidationException(DomainObjectValidationException exception){
        LOG.error("Domain validation error: {}", exception.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setTitle("Bad Request");

        return problemDetail;
    }
}
