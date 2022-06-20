package com.publicservice.converter;

import com.publicservice.dto.SubscriptionRsDTO;
import com.publicservice.sto.SubscriptionRsSTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionRsDtoConverter implements Converter<SubscriptionRsSTO, SubscriptionRsDTO> {
    @Override
    public SubscriptionRsDTO convert(final SubscriptionRsSTO source) {
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
