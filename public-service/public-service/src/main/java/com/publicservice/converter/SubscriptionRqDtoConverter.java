package com.publicservice.converter;

import com.publicservice.dto.SubscriptionRqDTO;
import com.publicservice.sto.SubscriptionRqSTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionRqDtoConverter implements Converter<SubscriptionRqDTO, SubscriptionRqSTO> {
    @Override
    public SubscriptionRqSTO convert(final SubscriptionRqDTO source) {
        return  SubscriptionRqSTO.builder()
                .birthDate(source.getBirthDate())
                .consent(source.isConsent())
                .email(source.getEmail())
                .firstName(source.getFirstName())
                .gender(source.getGender())
                .build();
    }
}
