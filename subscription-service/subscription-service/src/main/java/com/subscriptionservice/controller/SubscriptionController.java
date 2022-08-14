package com.subscriptionservice.controller;

import com.subscriptionservice.dto.SubscriptionRqDTO;
import com.subscriptionservice.dto.SubscriptionRsDTO;
import com.subscriptionservice.exception.dto.ErrorDetailsDTO;
import com.subscriptionservice.service.SubscriptionService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.IntStream;

@Api
@Slf4j
@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionService service;

    @PostMapping(path = "subscription", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create subscription", response = SubscriptionRqDTO.class)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created", response = SubscriptionRsDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorDetailsDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetailsDTO.class)
    })
    public ResponseEntity<SubscriptionRsDTO> createSubscription(@RequestBody @Valid final SubscriptionRqDTO request) {
        return new ResponseEntity<>(this.service.createSubscription(request), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "subscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieves subscriptions", notes = "", response = SubscriptionRsDTO.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SubscriptionRsDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorDetailsDTO.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorDetailsDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetailsDTO.class)
    })
    public ResponseEntity<List<SubscriptionRsDTO>> retrievesSubscriptions() {
        return new ResponseEntity<>(this.service.retrievesSubscription(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "subscription/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieve subscription by id", notes = "", response = SubscriptionRsDTO.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SubscriptionRsDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorDetailsDTO.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorDetailsDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetailsDTO.class)
    })
    public ResponseEntity<SubscriptionRsDTO> retrievesSubscription(@PathVariable final long id) {
        //parallel executions in orde to test the Bulkhead pattern
        IntStream.rangeClosed(1, 20).parallel().forEach(t->{
            this.service.retrieveSubscription(1);
        }
        );
        return new ResponseEntity<>(this.service.retrieveSubscription(id), HttpStatus.OK);
    }

    @PostMapping(path = "subscription/{id}/cancel")
    @ApiOperation(value = "Cancel subscription")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cancel Subscription"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorDetailsDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetailsDTO.class)
    })
    public ResponseEntity<Void> cancelSubscription(@PathVariable final long id) {
        this.service.cancelSubscription(id);
        return ResponseEntity.noContent().build();
    }
}
