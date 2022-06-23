package com.publicservice.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignClientInterceptor implements RequestInterceptor {


    private static final String AUTHORIZATION_HEADER = "Authorization";
    public static String TOKEN;


    public static String getToken() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
    }


    @Override
    public void apply(final RequestTemplate requestTemplate) {
        if (getToken() != null) {
            FeignClientInterceptor.TOKEN = getToken();
        }
        requestTemplate.header(AUTHORIZATION_HEADER, TOKEN);
    }
}