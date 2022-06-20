package com.emailservice.service.impl;

import com.emailservice.config.EmailConfig;
import com.emailservice.dto.EmailRqDTO;
import com.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailConfig emailConfig;

    @Override
    public void sendEmail(EmailRqDTO req) {
        log.debug("sending email with config data from host {}", emailConfig.getHost());
    }
}
