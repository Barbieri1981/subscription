package com.publicservice.controller;

import com.publicservice.dto.SubscriptionRqDTO;
import com.publicservice.dto.SubscriptionRsDTO;
import com.publicservice.exception.dto.ErrorDetailsDTO;
import com.publicservice.service.SubscriptionService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api
@Slf4j
@RestController
public class PublicServiceController {

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

    @GetMapping(path = "subscription/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retrieve subscription by id", notes = "", response = SubscriptionRsDTO.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SubscriptionRsDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorDetailsDTO.class),
            @ApiResponse(code = 404, message = "Resource not found", response = ErrorDetailsDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetailsDTO.class)
    })
    public ResponseEntity<SubscriptionRsDTO> retrievesSubscription(@PathVariable final long id) {
        return new ResponseEntity<>(this.service.retrieveSubscription(id), HttpStatus.OK);
    }

    @PutMapping(path = "subscription/{id}/cancel")
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
