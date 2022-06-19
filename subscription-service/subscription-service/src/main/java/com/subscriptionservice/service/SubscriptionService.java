package com.subscriptionservice.service;

import com.subscriptionservice.dto.SubscriptionRqDTO;
import com.subscriptionservice.dto.SubscriptionRsDTO;

import java.util.List;

public interface SubscriptionService {
    SubscriptionRsDTO createSubscription(final SubscriptionRqDTO request);
    List<SubscriptionRsDTO> retrievesSubscription();
    SubscriptionRsDTO retrieveSubscription(final long id);
}
