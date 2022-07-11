package com.subscriptionservice.factory;

import com.subscriptionservice.config.EmailConfig;
import com.subscriptionservice.dto.SubscriptionRqDTO;
import com.subscriptionservice.sto.EmailRqSTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailFactory {

    private final EmailConfig emailConfig;

    public EmailRqSTO createEmail(final SubscriptionRqDTO req) {
        final EmailRqSTO email= EmailRqSTO.builder()
                .email(req.getEmail())
                .message(this.emailConfig.getMessage())
                .subject(this.emailConfig.getSubject())
                .build();
        return email;
    }

}
