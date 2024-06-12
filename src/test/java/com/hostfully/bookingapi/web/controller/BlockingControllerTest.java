package com.hostfully.bookingapi.web.controller;

import com.hostfully.bookingapi.domain.Blocking;
import com.hostfully.bookingapi.domain.PeriodVO;
import com.hostfully.bookingapi.domain.Property;
import com.hostfully.bookingapi.usecases.BlockingUseCase;
import com.hostfully.bookingapi.web.dto.PeriodDto;
import com.hostfully.bookingapi.web.dto.PropertyDto;
import com.hostfully.bookingapi.web.mapper.BlockingDtoDomainMapper;
import com.hostfully.bookingapi.web.request.BlockingCreateRequest;
import com.hostfully.bookingapi.web.request.BlockingUpdateRequest;
import com.hostfully.bookingapi.web.response.BlockingResponse;
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
class BlockingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlockingUseCase useCase;

    @MockBean
    private BlockingDtoDomainMapper mapper;

    private Blocking domain;

    @BeforeEach
    void setUp(){
        Property property = new Property(1L, "Beach House");
        PeriodVO periodVO = new PeriodVO(LocalDate.now(), LocalDate.now().plusDays(10));
        domain = new Blocking(property, periodVO);
    }

    @Test
    void shouldReturnAllBlockingsAndStatus200() throws Exception {
        PropertyDto propertyDto = new PropertyDto(1L, "Beach House");
        PeriodDto periodDto = new PeriodDto(LocalDate.now(), LocalDate.now().plusDays(10));
        BlockingResponse blockingResponse = new BlockingResponse(1L, propertyDto, periodDto);

        when(useCase.getBlockingsByFilter(any())).thenReturn(Arrays.asList(domain));
        when(mapper.fromDomainToResponse(any())).thenReturn(blockingResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/blockings"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void shouldCreateBlockingAndReturnStatus201() throws Exception {
        String request = "{\"propertyId\":1,\"checkIn\":\"2024-03-12T00:00\",\"checkOut\":\"2024-03-18T00:00\"}";

        when(mapper.fromRequestToDomain(any(BlockingCreateRequest.class))).thenReturn(domain);
        doNothing().when(useCase).createBlocking(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/blockings")
                                              .content(request).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void shouldUpdateBlockingAndReturnStatus204() throws Exception {
        String request = "{\"checkIn\":\"2024-03-12T00:00\",\"checkOut\":\"2024-03-18T00:00\"}";
        PeriodDto periodDto = new PeriodDto(LocalDate.now(), LocalDate.now().plusDays(10));

        when(mapper.fromRequestToDomain(any(BlockingUpdateRequest.class))).thenReturn(periodDto);
        doNothing().when(useCase).createBlocking(any());

        mockMvc.perform(MockMvcRequestBuilders.put("/blockings/1")
                .content(request).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void shouldDeleteBlockingAndReturnStatus204() throws Exception {
        doNothing().when(useCase).deleteBlocking(any());

        mockMvc.perform(MockMvcRequestBuilders.delete("/blockings/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}