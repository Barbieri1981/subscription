package com.publicservice.exception;
import com.publicservice.enums.ErrorType;
import com.publicservice.exception.dto.ErrorDetailsDTO;
import com.publicservice.exception.dto.ErrorInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@ControllerAdvice
public class PublicServiceExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetailsDTO> handleException(final RuntimeException ex) {
        return createError(ex, ErrorType.PUBLIC_SERVICE_GENERIC_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PublicServiceException.class)
    public final ResponseEntity<ErrorDetailsDTO> handlePublicServiceException(final PublicServiceException ex) {
        return createError(ex, ex.getError(), HttpStatus.valueOf(ex.getHttpStatus()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorDetailsDTO> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String nombreCampo = ((FieldError) error).getField();
            String mensajeError = error.getDefaultMessage();
            errores.put(nombreCampo, mensajeError);
        });
        log.error("Error: [{}]", errores, ex);
        ErrorDetailsDTO detalleErrorDTO = new ErrorDetailsDTO(new ErrorInfoDTO(ErrorType.PUBLIC_SERVICE_SUBSCRIPTION_ERROR_DATA.getCode(),
                errores.toString(), ErrorType.PUBLIC_SERVICE_SUBSCRIPTION_ERROR_DATA.getDescription()));
        return new ResponseEntity<>(detalleErrorDTO, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorDetailsDTO> createError(final Exception ex, final ErrorType error, final HttpStatus httpStatus) {
        log.error("Error: [{}]", error, ex);
        ErrorDetailsDTO result = new ErrorDetailsDTO(new ErrorInfoDTO(error.getCode(), ex.getMessage(), error.getDescription()));
        return new ResponseEntity<>(result, httpStatus);
    }

}
