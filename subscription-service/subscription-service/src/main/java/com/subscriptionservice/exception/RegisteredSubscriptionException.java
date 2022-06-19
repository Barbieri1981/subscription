package com.subscriptionservice.exception;

import com.subscriptionservice.enums.ErrorType;
import org.springframework.http.HttpStatus;

public class RegisteredSubscriptionException extends SubscriptionException{

    private static final long serialVersionUID = 1L;

    public RegisteredSubscriptionException(final String message, final ErrorType error) {
        super(message, error, HttpStatus.BAD_REQUEST.value());
    }
}
