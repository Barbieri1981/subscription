package com.subscriptionservice.service.impl;

import com.subscriptionservice.client.EmailClient;
import com.subscriptionservice.config.EmailConfig;
import com.subscriptionservice.dto.SubscriptionRqDTO;
import com.subscriptionservice.service.EmailService;
import com.subscriptionservice.sto.EmailRqSTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailConfig emailConfig;
    private final EmailClient emailClient;

    @Override
    public void sendEmail(final SubscriptionRqDTO req) {
        final EmailRqSTO email = createEmail(req);
        log.debug("Sending email {}", email);
        this.emailClient.sendEmail(email);
    }

    private EmailRqSTO createEmail(final SubscriptionRqDTO req) {
        final EmailRqSTO email= EmailRqSTO.builder()
                .email(req.getEmail())
                .message(this.emailConfig.getMessage())
                .subject(this.emailConfig.getSubject())
                .build();
        return email;
    }
}
