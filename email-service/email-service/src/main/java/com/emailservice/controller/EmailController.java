package com.emailservice.controller;

import com.emailservice.Exception.dto.ErrorDetailsDTO;
import com.emailservice.dto.EmailRqDTO;
import com.emailservice.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api
@Slf4j
@RestController
public class EmailController {

    @Autowired
    private EmailService service;

    @PostMapping(path = "send-email", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Send email")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Send Email"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorDetailsDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDetailsDTO.class)
    })
    public ResponseEntity<Void> sendEmail(@RequestBody @Valid final EmailRqDTO request) {
        this.service.sendEmail(request);
        return ResponseEntity.noContent().build();
    }

}
