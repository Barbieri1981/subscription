package com.publicservice.service.impl;

import com.publicservice.client.SubscriptionClient;
import com.publicservice.converter.SubscriptionRqDtoConverter;
import com.publicservice.converter.SubscriptionRsDtoConverter;
import com.publicservice.dto.SubscriptionRqDTO;
import com.publicservice.dto.SubscriptionRsDTO;
import com.publicservice.service.SubscriptionService;
import com.publicservice.sto.SubscriptionRqSTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionClient client;
    private final SubscriptionRqDtoConverter subscriptionRqConverter;
    private final SubscriptionRsDtoConverter subscriptionRsConverter;

    @Override
    public SubscriptionRsDTO createSubscription(SubscriptionRqDTO request) {
        log.debug("Creating subscription {}", request);
        SubscriptionRqSTO requestSto = this.subscriptionRqConverter.convert(request);
        return this.subscriptionRsConverter.convert(this.client.createSubscription(requestSto).getBody());
    }

    @Override
    public List<SubscriptionRsDTO> retrievesSubscription() {
        log.debug("Retrieving subscriptions");
        return this.client.retrievesSubscriptions().getBody().stream().map(this.subscriptionRsConverter::convert).collect(Collectors.toList());
    }

    @Override
    public SubscriptionRsDTO retrieveSubscription(long id) {
        log.debug("Retrieve subscription by id {}", id);
        return this.subscriptionRsConverter.convert(this.client.retrievesSubscription(id).getBody());
    }

    @Override
    public void cancelSubscription(long id) {
        log.debug("Cancel subscription {}", id);
        this.client.cancelSubscription(id);
    }

}
