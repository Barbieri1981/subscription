package com.publicservice.client;

import com.publicservice.sto.SubscriptionRqSTO;
import com.publicservice.sto.SubscriptionRsSTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "subscription-service", name = "subscription-service", configuration = FeignClientInterceptor.class)
public interface SubscriptionClient {

    @PostMapping(path = "subscription", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubscriptionRsSTO> createSubscription(@RequestBody final SubscriptionRqSTO request);

    @GetMapping(path = "subscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SubscriptionRsSTO>> retrievesSubscriptions();

    @GetMapping(path = "subscription/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubscriptionRsSTO> retrievesSubscription(@PathVariable final long id);

    @PutMapping(path = "subscription/{id}/cancel")
    public ResponseEntity<Void> cancelSubscription(@PathVariable final long id);
}
