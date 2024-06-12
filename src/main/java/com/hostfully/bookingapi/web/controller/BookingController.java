package com.hostfully.bookingapi.web.controller;

import com.hostfully.bookingapi.domain.Booking;
import com.hostfully.bookingapi.usecases.BookingUseCase;
import com.hostfully.bookingapi.web.mapper.BookingDtoDomainMapper;
import com.hostfully.bookingapi.web.request.BookingCreateRequest;
import com.hostfully.bookingapi.web.request.BookingFilter;
import com.hostfully.bookingapi.web.request.BookingUpdateRequest;
import com.hostfully.bookingapi.web.response.BookingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
@Tag(name = "Bookings")
public class BookingController {

    private static final Logger LOG = LoggerFactory.getLogger(BookingController.class);

    private final BookingUseCase useCase;

    private final BookingDtoDomainMapper mapper;

    @Operation(summary = "Get Bookings by filter", description = "Returns Bookings by filter")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings(@RequestParam(required = false) Long propertyId,
                                                                @RequestParam(required = false) Long guestId){
        LOG.info("Receiving a GET to return Bookings by filter");

        List<Booking> allBookings = useCase.getBookingsByFilter(new BookingFilter(propertyId, guestId));
        List<BookingResponse> responseBody = allBookings.stream().map(booking -> mapper.fromDomainToResponse(booking)).toList();

        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "Get a Booking by id", description = "Returns a Booking as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The Booking was not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable @Parameter(name = "id", description = "Booking id") Long id){
        LOG.info("Receiving a GET to return Booking with id {}", id);

        Booking booking = useCase.getBookingById(id);
        BookingResponse responseBody = mapper.fromDomainToResponse(booking);

        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "Create Booking", description = "Creating a Booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found - The Property/Guest was not found")
    })
    @PostMapping
    public ResponseEntity<Void> createBooking(@RequestBody @Valid BookingCreateRequest request){
        LOG.info("Receiving a POST to create Booking");

        Booking booking = mapper.fromRequestToDomain(request);
        useCase.createBooking(booking);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update Booking", description = "Updating a Booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found - The Booking/Guest was not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBooking(@PathVariable @Parameter(name = "id", description = "Booking id") Long id,
                                              @RequestBody @Valid BookingUpdateRequest request){
        LOG.info("Receiving a PUT to update Booking with id {}", id);

        Booking booking = mapper.fromRequestToDomain(request);
        useCase.updateBooking(id, booking);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cancel Booking", description = "Canceling a Booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Not found - The Booking was not found")
    })
    @PatchMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable @Parameter(name = "id", description = "Booking id") Long id){
        LOG.info("Receiving a PATCH to cancel Booking with id {}", id);

        useCase.cancelBooking(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Activate Booking", description = "Activating a Booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully activated"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found - The Booking was not found")
    })
    @PatchMapping("/activate/{id}")
    public ResponseEntity<Void> activateBooking(@PathVariable @Parameter(name = "id", description = "Booking id") Long id){
        LOG.info("Receiving a PATCH to activate Booking with id {}", id);

        useCase.activateBooking(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete Booking", description = "Deleting a Booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully canceled"),
            @ApiResponse(responseCode = "404", description = "Not found - The Booking was not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable @Parameter(name = "id", description = "Booking id") Long id){
        LOG.info("Receiving a DELETE to delete Booking with id {}", id);

        useCase.deleteBooking(id);

        return ResponseEntity.noContent().build();
    }
}
