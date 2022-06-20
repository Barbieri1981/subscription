package com.subscriptionservice.client;


import com.subscriptionservice.sto.EmailRqSTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient("email-service")
public interface EmailClient {

    @PostMapping(path = "send-email", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> sendEmail(@RequestBody @Valid final EmailRqSTO request);
}
