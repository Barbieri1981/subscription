package com.publicservice.config;

import com.publicservice.decoder.CustomErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public CustomErrorDecoder customFeignDecoder() {
        return new CustomErrorDecoder();
    }
}

