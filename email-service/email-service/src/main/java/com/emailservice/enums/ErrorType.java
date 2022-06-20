package com.emailservice.enums;

public enum ErrorType {

    EMAIL_SERVICE_GENERIC_ERROR("email_service_001", "Unexpected error"),
    EMAIL_SERVICE_ERROR_DATA("email_service_002", "Error while validating data");

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
