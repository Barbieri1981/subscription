package com.subscriptionservice.exception;

import com.subscriptionservice.enums.ErrorType;
import org.springframework.http.HttpStatus;

public class SubscriptionNotFound extends SubscriptionException {

    private static final long serialVersionUID = 1L;

    public SubscriptionNotFound(final String message, final ErrorType error) {
        super(message, error, HttpStatus.NOT_FOUND.value());
    }

}
