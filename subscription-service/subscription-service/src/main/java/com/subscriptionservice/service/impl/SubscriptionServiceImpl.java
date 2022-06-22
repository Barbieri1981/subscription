package com.subscriptionservice.service.impl;

import com.subscriptionservice.converter.SubscriptionConverter;
import com.subscriptionservice.converter.SubscriptionRqConverter;
import com.subscriptionservice.dto.SubscriptionRqDTO;
import com.subscriptionservice.dto.SubscriptionRsDTO;
import com.subscriptionservice.entity.Subscription;
import com.subscriptionservice.enums.ErrorType;
import com.subscriptionservice.exception.CircuitBreakerException;
import com.subscriptionservice.exception.RegisteredSubscriptionException;
import com.subscriptionservice.exception.SubscriptionNotFound;
import com.subscriptionservice.repository.SubscriptionRepository;
import com.subscriptionservice.service.EmailService;
import com.subscriptionservice.service.SubscriptionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
    private final EmailService emailService;

    @CircuitBreaker(name = "create-subscription-circuit-breaker", fallbackMethod = "createSubscriptionFallbackMethod")
    @Override
    public SubscriptionRsDTO createSubscription(final SubscriptionRqDTO request) {
        log.debug("Creating subscription: {}", request);
        validateData(request);
        final Subscription subscription = this.repository.save(this.subscriptionRqConverter.convert(request));
        this.emailService.sendEmail(request);
        return this.subscriptionConverter.convert(subscription);
    }

    private SubscriptionRsDTO createSubscriptionFallbackMethod(final SubscriptionRqDTO request, Throwable e) {
        log.debug("Create subscriptions fallback method {}", e.getMessage());
        throw new CircuitBreakerException("Subscription Service is down", ErrorType.SUBSCRIPTION_IS_DOWN);
    }

    private void validateData(final SubscriptionRqDTO request) {
        log.debug("Validating data {}", request);
        Optional<Subscription> result = this.repository.findByEmail(request.getEmail());
        if (result.isPresent()) {
            throw new RegisteredSubscriptionException("Subscription exists", ErrorType.SUBSCRIPTION_EXISTS);
        }
    }

    @RateLimiter(name = "retrieves-subscriptions-rate-limiter", fallbackMethod = "retrievesSubscriptionFallbackMethod")
    @Override
    public List<SubscriptionRsDTO> retrievesSubscription() {
        log.debug("Retrieving subscriptions");
        return this.repository.findAll().stream().map(subscriptionConverter::convert).collect(Collectors.toList());
    }

    @Override
    public SubscriptionRsDTO retrieveSubscription(final long id) {
        return this.subscriptionConverter.convert(findSubscriptionById(id));
    }

    @Override
    public void cancelSubscription(final long id) {
        final Subscription subscription = findSubscriptionById(id);
        log.debug("Canceling subscription {}", subscription);
        subscription.setConsent(false);
        this.repository.save(subscription);
    }

    private Subscription findSubscriptionById(final long id) {
        log.debug("Retrieving subscription by id {}", id);
        return this.repository.findById(id)
                .orElseThrow(()->new SubscriptionNotFound("Subscription not found", ErrorType.SUBSCRIPTION_NOT_FOUND));
    }

    private List<SubscriptionRsDTO> retrievesSubscriptionFallbackMethod(Throwable e) {
        log.debug("Retrieving subscriptions fallback method {}", e.getMessage());
        return new ArrayList<>(Collections.singleton(SubscriptionRsDTO.builder().build()));
    }

}
