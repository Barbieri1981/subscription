package com.publicservice.client;

import com.publicservice.sto.SubscriptionRqSTO;
import com.publicservice.sto.SubscriptionRsSTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("subscription-service")
public interface SubscriptionClient {

    @PostMapping(path = "subscription", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubscriptionRsSTO> createSubscription(@RequestBody final SubscriptionRqSTO request);

    @GetMapping(path = "subscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SubscriptionRsSTO>> retrievesSubscriptions();

    @GetMapping(path = "subscription/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubscriptionRsSTO> retrievesSubscription(@PathVariable final long id);

    @PostMapping(path = "subscription/{id}/cancel")
    public ResponseEntity<Void> cancelSubscription(@PathVariable final long id);
}