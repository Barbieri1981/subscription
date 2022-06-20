package com.publicservice.exception;

import com.publicservice.enums.ErrorType;
import org.springframework.http.HttpStatus;

public class SubscriptionNotFound extends PublicServiceException {

    private static final long serialVersionUID = 1L;

    public SubscriptionNotFound(final String message, final ErrorType error) {
        super(message, error, HttpStatus.NOT_FOUND.value());
    }

}
