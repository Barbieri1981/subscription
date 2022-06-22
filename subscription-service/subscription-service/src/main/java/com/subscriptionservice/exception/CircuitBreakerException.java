package com.subscriptionservice.exception;

import com.subscriptionservice.enums.ErrorType;
import org.springframework.http.HttpStatus;

public class CircuitBreakerException extends SubscriptionException{

    private static final long serialVersionUID = 1L;

    public CircuitBreakerException(final String message, final ErrorType error) {
        super(message, error, HttpStatus.FORBIDDEN.value());
    }
}
