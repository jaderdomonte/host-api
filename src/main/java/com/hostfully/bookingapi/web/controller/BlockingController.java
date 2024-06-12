package com.hostfully.bookingapi.web.controller;

import com.hostfully.bookingapi.domain.Blocking;
import com.hostfully.bookingapi.usecases.BlockingUseCase;
import com.hostfully.bookingapi.web.dto.PeriodDto;
import com.hostfully.bookingapi.web.mapper.BlockingDtoDomainMapper;
import com.hostfully.bookingapi.web.request.BlockingCreateRequest;
import com.hostfully.bookingapi.web.request.BlockingUpdateRequest;
import com.hostfully.bookingapi.web.response.BlockingResponse;
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
@RequestMapping("/blockings")
@AllArgsConstructor
@Tag(name = "Blockings")
public class BlockingController {

    private static final Logger LOG = LoggerFactory.getLogger(BlockingController.class);

    private final BlockingUseCase useCase;

    private final BlockingDtoDomainMapper mapper;

    @Operation(summary = "Get Blockings by filter", description = "Returns Blockings by filter")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    @GetMapping
    public ResponseEntity<List<BlockingResponse>> getAllBlockings(@RequestParam(required = false) Long propertyId){
        LOG.info("Receiving a GET to return Blockings by filter");
        List<Blocking> blockingsDomain = useCase.getBlockingsByFilter(propertyId);
        List<BlockingResponse> responseBody = blockingsDomain.stream().map(blocking -> mapper.fromDomainToResponse(blocking)).toList();

        return ResponseEntity.ok(responseBody);
    }

    @Operation(summary = "Create Blocking", description = "Creating a Blocking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found - The Property was not found")
    })
    @PostMapping
    public ResponseEntity<Void> createBlocking(@RequestBody @Valid BlockingCreateRequest request){
        LOG.info("Receiving a POST to create Blocking");
        Blocking blocking = mapper.fromRequestToDomain(request);
        useCase.createBlocking(blocking);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update Blocking", description = "Updating a Blocking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found - The Blocking was not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBlocking(@PathVariable @Parameter(name = "id", description = "Blocking id") Long id,
                                               @Valid @RequestBody BlockingUpdateRequest request){
        LOG.info("Receiving a PUT to update Blocking with id {}", id);
        PeriodDto periodDto = mapper.fromRequestToDomain(request);

        useCase.updateBlocking(id, periodDto);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete Blocking", description = "Deleting a Blocking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Not found - The Blocking was not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlocking(@PathVariable @Parameter(name = "id", description = "Blocking id") Long id){
        LOG.info("Receiving a DELETE to delete Blocking with id {}", id);
        useCase.deleteBlocking(id);

        return ResponseEntity.noContent().build();
    }
}
