package com.hostfully.bookingapi.web.controller;

import com.hostfully.bookingapi.db.enumeration.BookingStatusEnum;
import com.hostfully.bookingapi.domain.*;
import com.hostfully.bookingapi.usecases.BookingUseCase;
import com.hostfully.bookingapi.web.dto.GuestDto;
import com.hostfully.bookingapi.web.dto.PeriodDto;
import com.hostfully.bookingapi.web.dto.PropertyDto;
import com.hostfully.bookingapi.web.mapper.BookingDtoDomainMapper;
import com.hostfully.bookingapi.web.request.BookingCreateRequest;
import com.hostfully.bookingapi.web.request.BookingUpdateRequest;
import com.hostfully.bookingapi.web.response.BookingResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingUseCase useCase;

    @MockBean
    private BookingDtoDomainMapper mapper;

    private Booking domain;

    private BookingResponse response;

    @BeforeEach
    void setUp(){
        GuestNameVO guestNameVO = new GuestNameVO("Jordan", "Love");
        Guest guest = new Guest(1L, guestNameVO);
        Property property = new Property(1L, "Beach House");
        PeriodVO periodVO = new PeriodVO(LocalDate.now(), LocalDate.now().plusDays(10));
        BookingStatusVO bookingStatusVO = new BookingStatusVO(BookingStatusEnum.CANCELED.getId(), BookingStatusEnum.CANCELED.getDescription());

        domain = new Booking(1L, guest, property, periodVO, bookingStatusVO);

        GuestDto guestDto = new GuestDto(domain.getGuest().getId(), domain.getGuest().getFullName().getFirstName(), domain.getGuest().getFullName().getLastName());
        PeriodDto periodDto = new PeriodDto(domain.getPeriod().getCheckIn(), domain.getPeriod().getCheckOut());
        PropertyDto propertyDto = new PropertyDto(domain.getProperty().getId(), domain.getProperty().getName());
        response = new BookingResponse(domain.getId(), guestDto, propertyDto, periodDto, domain.getStatus().getDescription());
    }

    @Test
    void shouldReturnBookingsByFilterAndStatus200() throws Exception {
        when(useCase.getBookingsByFilter(any())).thenReturn(Arrays.asList(domain));
        when(mapper.fromDomainToResponse(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/bookings")
                                            .param("propertyId", "1")
                                            .param("guestId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void shouldReturnBookingAndStatus200() throws Exception {
        when(useCase.getBookingById(any())).thenReturn(domain);
        when(mapper.fromDomainToResponse(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/bookings/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    void shouldCreateBookingAndReturnStatus201() throws Exception {
        String request = "{\"propertyId\":1,\"guestId\":1,\"checkIn\":\"2024-03-12T00:00\",\"checkOut\":\"2024-03-22T00:00\"}";

        when(mapper.fromRequestToDomain(any(BookingCreateRequest.class))).thenReturn(domain);
        doNothing().when(useCase).createBooking(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/bookings")
                .content(request).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void shouldUpdateBookingAndReturnStatus204() throws Exception {
        String request = "{\"guestId\":1,\"checkIn\":\"2024-03-12T00:00\",\"checkOut\":\"2024-03-22T00:00\"}";

        when(mapper.fromRequestToDomain(any(BookingUpdateRequest.class))).thenReturn(domain);
        doNothing().when(useCase).updateBooking(any(), any());

        mockMvc.perform(MockMvcRequestBuilders.put("/bookings/1")
                .content(request).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void shouldCancelBookingAndReturnStatus204() throws Exception {
        doNothing().when(useCase).cancelBooking(any());

        mockMvc.perform(MockMvcRequestBuilders.patch("/bookings/cancel/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void shouldActivateBookingAndReturnStatus204() throws Exception {
        doNothing().when(useCase).activateBooking(any());

        mockMvc.perform(MockMvcRequestBuilders.patch("/bookings/activate/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void shouldDeleteBookingAndReturnStatus204() throws Exception {
        doNothing().when(useCase).deleteBooking(any());

        mockMvc.perform(MockMvcRequestBuilders.delete("/bookings/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}