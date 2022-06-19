package com.subscriptionservice.exception;

import com.subscriptionservice.enums.ErrorType;

public class SubscriptionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected final ErrorType error;
    protected final int httpStatus;

    public SubscriptionException(final String message, final ErrorType error, final int httpStatus) {
        super(message);
        this.error = error;
        this.httpStatus = httpStatus;
    }

    public ErrorType getError() {
        return this.error;
    }


    public int getHttpStatus() {
        return this.httpStatus;
    }

}
