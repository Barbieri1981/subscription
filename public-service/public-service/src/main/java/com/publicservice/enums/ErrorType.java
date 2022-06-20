package com.publicservice.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ErrorType {

    PUBLIC_SERVICE_GENERIC_ERROR("public_service_001", "Unexpected error"),
    PUBLIC_SERVICE_SUBSCRIPTION_EXISTS("public_service_002", "subscription exists"),
    PUBLIC_SERVICE_SUBSCRIPTION_ERROR_DATA("public_service_003", "Error while validating data"),
    PUBLIC_SERVICE_SUBSCRIPTION_NOT_FOUND("public_service_004", "Subscriptions not found"),
    SUBSCRIPTION_GENERIC_ERROR("subscription_001", "Unexpected error"),
    SUBSCRIPTION_EXISTS("subscription_002", "subscription exists"),
    SUBSCRIPTION_ERROR_DATA("subscription_003", "Error while validating data"),
    SUBSCRIPTION_NOT_FOUND("subscription_004", "Subscriptions not found"),
    EMAIL_SERVICE_GENERIC_ERROR("email_service_001", "Unexpected error"),
    EMAIL_SERVICE_ERROR_DATA("email_service_002", "Error while validating data");

    private static final Map<String, ErrorType> mapError = createMapError();

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

    public static ErrorType resolveByErrorCode(final String code) {
        return mapError.get(code);
    }

    private static Map<String, ErrorType> createMapError() {
        return Arrays.stream(values()).collect(Collectors.toMap(ErrorType::getCode, e -> e));
    }
}
