package com.subscriptionservice.converter;

import com.subscriptionservice.dto.SubscriptionRqDTO;
import com.subscriptionservice.entity.Subscription;
import com.subscriptionservice.enums.GenderType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionRqConverter implements Converter<SubscriptionRqDTO, Subscription> {
    @Override
    public Subscription convert(final SubscriptionRqDTO source) {
        return  Subscription.builder()
                .birthDate(source.getBirthDate())
                .consent(source.isConsent())
                .email(source.getEmail())
                .firstName(source.getFirstName())
                .gender(GenderType.resolveByGender(source.getGender()))
                .build();
    }
}
