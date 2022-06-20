package com.publicservice.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.publicservice.enums.ErrorType;
import com.publicservice.exception.PublicServiceException;
import com.publicservice.exception.SubscriptionNotFound;
import com.publicservice.exception.dto.ErrorDetailsDTO;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.InputStreamReader;
import java.util.function.Predicate;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Predicate<Response> badRequest = response -> response.status() == HttpStatus.BAD_REQUEST.value();
    private static final Predicate<Response> forbidden = response -> response.status() == HttpStatus.FORBIDDEN.value();
    private static final Predicate<Response> notFound = response -> response.status() == HttpStatus.NOT_FOUND.value();


    @Override
    public Exception decode(final String methodKey, final Response response) {

        ErrorType error = ErrorType.PUBLIC_SERVICE_GENERIC_ERROR;
        String errorMessage = null;

        try {
            final String payload = CharStreams.toString(new InputStreamReader(response.body().asInputStream(), Charsets.UTF_8));
            final ErrorDetailsDTO errorDetails = objectMapper.readValue(payload, ErrorDetailsDTO.class);
            if (errorDetails != null && errorDetails.getError() != null) {
                error = ErrorType.resolveByErrorCode(errorDetails.getError().getCode());
                errorMessage = errorDetails.getError().getMessage();
            }
        } catch (final Exception e) {
            log.error("Error trying to obtain error message", e);
        }

        return createException(response, error, errorMessage);
    }

    private PublicServiceException createException(final Response response, final ErrorType error, final String errorMessage) {
        if (isForbidden(response)) {
            return new PublicServiceException(errorMessage, error, HttpStatus.FORBIDDEN.value());
        }
        if (isBadRequest(response)) {
            return new PublicServiceException(errorMessage, error, HttpStatus.BAD_REQUEST.value());
        }
        if (isNotFound(response)) {
            return new SubscriptionNotFound(errorMessage, error);
        }

        return new PublicServiceException(errorMessage, error, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    private boolean isBadRequest(final Response response) {
        return badRequest.test(response);
    }

    private boolean isForbidden(final Response response) {return forbidden.test(response);}

    private boolean isNotFound(final Response response) {
        return notFound.test(response);
    }
}
