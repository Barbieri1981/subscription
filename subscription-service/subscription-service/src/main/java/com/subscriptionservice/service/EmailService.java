package com.subscriptionservice.service;

import com.subscriptionservice.dto.SubscriptionRqDTO;

public interface EmailService {
    public  void sendEmail(final SubscriptionRqDTO req);
    public  void retriesSendingEmail(final SubscriptionRqDTO req);
}
