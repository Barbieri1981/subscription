package com.subscriptionservice.service.impl;

import com.subscriptionservice.client.EmailClient;
import com.subscriptionservice.dto.SubscriptionRqDTO;
import com.subscriptionservice.factory.EmailFactory;
import com.subscriptionservice.service.EmailService;
import com.subscriptionservice.sto.EmailRqSTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    public static final String SEND_EMAIL_CIRCUIT_BREAKER = "send-email-circuit-breaker";
    private final EmailFactory emailFactory;
    private final EmailClient emailClient;

    @CircuitBreaker(name = SEND_EMAIL_CIRCUIT_BREAKER, fallbackMethod = "sendEmailCircuitBreaker")
    @Override
    public void sendEmail(final SubscriptionRqDTO req) {
        final EmailRqSTO email = this.emailFactory.createEmail(req);
        log.debug("Sending email {}", email);
        this.emailClient.sendEmail(email);
    }

    private void sendEmailCircuitBreaker(final SubscriptionRqDTO req, Throwable e) {
        log.info("circuit breaker :: sending email by another email provider {}",e.getMessage());
    }

}
