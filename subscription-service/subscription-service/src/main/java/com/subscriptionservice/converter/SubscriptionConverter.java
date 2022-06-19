package com.subscriptionservice.converter;

import com.subscriptionservice.dto.SubscriptionRsDTO;
import com.subscriptionservice.entity.Subscription;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionConverter implements Converter<Subscription, SubscriptionRsDTO> {
    @Override
    public SubscriptionRsDTO convert(final Subscription source) {
        return  SubscriptionRsDTO.builder()
                .id(source.getId())
                .birthDate(source.getBirthDate())
                .consent(source.isConsent())
                .email(source.getEmail())
                .firstName(source.getFirstName())
                .gender(source.getGender())
                .build();
    }
}
