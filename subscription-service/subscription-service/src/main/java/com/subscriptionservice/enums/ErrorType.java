package com.subscriptionservice.enums;

public enum ErrorType {

    SUBSCRIPTION_GENERIC_ERROR("subscription_001", "Unexpected error"),
    SUBSCRIPTION_EXISTS("subscription_002", "subscription exists"),
    SUBSCRIPTION_ERROR_DATA("subscription_003", "Error while validating data"),
    SUBSCRIPTION_NOT_FOUND("subscription_004", "Subscriptions not found");

    private final String code;
    private final String description;

    private ErrorType(final String code, final String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return new StringBuilder("ErrorType [name: ").append(this.name()).append(", code: ").append(this.code).append(", description: ")
                .append(this.description).append("]").toString();
    }
}
