package com.subscriptionservice.service.impl;

import com.subscriptionservice.converter.SubscriptionConverter;
import com.subscriptionservice.converter.SubscriptionRqConverter;
import com.subscriptionservice.dto.SubscriptionRqDTO;
import com.subscriptionservice.dto.SubscriptionRsDTO;
import com.subscriptionservice.entity.Subscription;
import com.subscriptionservice.enums.ErrorType;
import com.subscriptionservice.exception.RegisteredSubscriptionException;
import com.subscriptionservice.exception.SubscriptionNotFound;
import com.subscriptionservice.repository.SubscriptionRepository;
import com.subscriptionservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRqConverter subscriptionRqConverter;
    private final SubscriptionRepository repository;
    private final SubscriptionConverter subscriptionConverter;

    @Override
    public SubscriptionRsDTO createSubscription(final SubscriptionRqDTO request) {
        log.debug("Creating subscription: {}", request);
        validateData(request);
        Subscription subscription = this.repository.save(this.subscriptionRqConverter.convert(request));
        return this.subscriptionConverter.convert(subscription);
    }

    private void validateData(final SubscriptionRqDTO request) {
        Optional<Subscription> result = this.repository.findByEmail(request.getEmail());
        if (result.isPresent()) {
            throw new RegisteredSubscriptionException("Subscription exists", ErrorType.SUBSCRIPTION_EXISTS);
        }
    }


    @Override
    public List<SubscriptionRsDTO> retrievesSubscription() {
        log.debug("Retrieving subscriptions");
        return this.repository.findAll().stream().map(subscriptionConverter::convert).collect(Collectors.toList());
    }

    @Override
    public SubscriptionRsDTO retrieveSubscription(long id) {
        log.info("Retrieving subscription by id {}", id);
        return this.subscriptionConverter.convert(this.repository.findById(id)
                .orElseThrow(()->new SubscriptionNotFound("Subscription not found", ErrorType.SUBSCRIPTION_NOT_FOUND)));
    }

}
