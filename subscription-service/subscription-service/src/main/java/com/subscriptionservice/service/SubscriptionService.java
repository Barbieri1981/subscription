package com.subscriptionservice.service;

import com.subscriptionservice.dto.SubscriptionRqDTO;
import com.subscriptionservice.dto.SubscriptionRsDTO;

import java.util.List;
import java.util.concurrent.TimeoutException;

public interface SubscriptionService {
    SubscriptionRsDTO createSubscription(final SubscriptionRqDTO request);
    List<SubscriptionRsDTO> retrievesSubscription();
    SubscriptionRsDTO retrieveSubscription(final long id);
    void cancelSubscription(final long id) throws TimeoutException;
}
