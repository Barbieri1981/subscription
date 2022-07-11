package com.subscriptionservice.service.impl;

import com.subscriptionservice.client.EmailClient;
import com.subscriptionservice.dto.SubscriptionRqDTO;
import com.subscriptionservice.factory.EmailFactory;
import com.subscriptionservice.service.EmailService;
import com.subscriptionservice.sto.EmailRqSTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailFactory emailFactory;
    private final EmailClient emailClient;

    @CircuitBreaker(name = "send-email-circuit-breaker", fallbackMethod = "sendEmailCircuitBreaker")
    @Retry(name = "send-email-retry", fallbackMethod = "sendEmailRetry")
    @Override
    public void sendEmail(final SubscriptionRqDTO req) {
        final EmailRqSTO email = this.emailFactory.createEmail(req);
        log.debug("Sending email {}", email);
        this.emailClient.sendEmail(email);
    }

    @Retry(name = "retries-sending-email", fallbackMethod = "retriesSendingEmailRetry")
    @Override
    public void retriesSendingEmail(SubscriptionRqDTO req) {
        final EmailRqSTO email = this.emailFactory.createEmail(req);
        log.debug("Sending email {}", email);
        this.emailClient.sendEmail(email);
    }

    private void sendEmailCircuitBreaker(final SubscriptionRqDTO req, Throwable e) {
        log.info("circuit breaker :: sending email by another email provider {}",e.getMessage());
    }

    private void sendEmailRetry(final SubscriptionRqDTO req, Throwable e) {
        log.info("retry :: sending email by another email provider {}",e.getMessage());
    }

    private void retriesSendingEmailRetry(final SubscriptionRqDTO req, Throwable e) {
        log.info("retry :: retries sending email {}",e.getMessage());
    }
}
