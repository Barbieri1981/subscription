package com.emailservice.service;

import com.emailservice.dto.EmailRqDTO;

public interface EmailService {
    public  void sendEmail(final EmailRqDTO req);
}
