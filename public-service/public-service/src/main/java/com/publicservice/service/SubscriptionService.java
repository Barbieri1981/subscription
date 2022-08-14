package com.publicservice.service;

import com.publicservice.dto.SubscriptionRqDTO;
import com.publicservice.dto.SubscriptionRsDTO;

import java.util.List;

public interface SubscriptionService {
    SubscriptionRsDTO createSubscription(final SubscriptionRqDTO request);
    List<SubscriptionRsDTO> retrievesSubscription();
    SubscriptionRsDTO retrieveSubscription(final long id);
    void cancelSubscription(final long id);
}
