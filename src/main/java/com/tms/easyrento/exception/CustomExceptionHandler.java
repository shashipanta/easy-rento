package com.tms.easyrento.exception;

import com.tms.easyrento.config.CustomMessageSource;
import com.tms.easyrento.globals.GlobalApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/12/23 10:29 PM
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler  {

    private final CustomMessageSource messageSource;

    private static final String PREFIX_UNIQUE = "uk";
    private static final String PREFIX_FOREIGN = "fk";

    private static final String NOT_NULL = "NotNull";
    private static final String CONDITIONAL_NOT_NULL = "ConditionalNotNull";
    private static final String NOT_BLANK = "NotBlank";
    private static final String NOT_EMPTY = "NotEmpty";

    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalExceptionResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            validation(errors, error);  // your custom error formatting method
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        return new ResponseEntity<>(genericWithMessage(HttpStatus.BAD_REQUEST,
                errors.get(0), errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<GlobalExceptionResponse> handleDataIntegrityExceptions(
            final DataIntegrityViolationException dive) {
        final List<String> errors = new ArrayList<>();
        Throwable cause = dive.getCause();

        if (cause instanceof ConstraintViolationException cve) {
            extractMessageFromViolation(errors, cve);
        }

        return new ResponseEntity<>(genericWithMessage(HttpStatusCode.valueOf(400),
                errors.get(0), errors), HttpStatus.BAD_REQUEST);
    }

    private void extractMessageFromViolation(List<String> errors, ConstraintViolationException cve) {
        String constraintName = cve.getConstraintName();
        String[] split = constraintName.split("_");
        String columnName = split[split.length - 1];
        String stringConstCode = columnName.replace("-", ".");
        String errorMessage = "";

        String errPrefix = split[0];

        if (PREFIX_UNIQUE.equals(errPrefix)) {
            try {
                errorMessage = messageSource.get("exists", messageSource.get(stringConstCode));
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            errors.add(errorMessage);
            cve.printStackTrace();

        }
    }


    private GlobalExceptionResponse genericWithMessage(HttpStatusCode httpStatusCode,
                                                       String message,
                                                       List<String> errors) {
        return new GlobalExceptionResponse("FAILURE", httpStatusCode.toString(), message, errors);

    }


    /**
     * Handle Bean validation with respective messages
     *
     * @param errors     List of validation errors
     * @param fieldError single <code>FieldError</code>
     */
    private void validation(List<String> errors, FieldError fieldError) {
        String errorCode = fieldError.getCode();
        String errorField = fieldError.getField().toLowerCase();
        Object rejectedValue = fieldError.getRejectedValue();
        String defaultMessage = fieldError.getDefaultMessage();
        if (defaultMessage != null && !defaultMessage.isEmpty())
            defaultMessage = fieldError.getDefaultMessage();
        else defaultMessage = "";

        Object[] errArguments = fieldError.getArguments();

        String errorFieldValidationCode = "";
        try {
            errorFieldValidationCode = messageSource.get(errorField);
        } catch (Exception e) {
            try {
                errorFieldValidationCode = messageSource.get(errorField.toUpperCase());
            } catch (Exception ex) {
                errorFieldValidationCode = errorField;
            }
        }
        validateTypes(errorCode, errArguments, rejectedValue, errorFieldValidationCode, defaultMessage, errors);
    }

    private void validateTypes(String errorCode, Object[] errArguments,
                               Object rejectedValue, String errorFieldValidationCode,
                               String defaultMessage, List<String> errors) {
        try {
            switch (errorCode) {
                case NOT_NULL, CONDITIONAL_NOT_NULL,
                     NOT_BLANK, NOT_EMPTY ->
                    /*
                        defaultMessage = {0} not.null
                        errorFieldValidationCode = phone
                        message = Phone cannot be null
                     */
                        errors.add(messageSource.get(defaultMessage, errorFieldValidationCode));

                case "Email" -> errors.add(messageSource.get(defaultMessage, errorFieldValidationCode, rejectedValue));
                case "Size" -> {
                    // TODO: extract size parameters and add to message
                }
                default -> errors.add(defaultMessage);

            }
        } catch (Exception e) {
            // in case message source does not have required code
            errors.add(defaultMessage);
        }
    }

    /**
     * Authentication exception
     */
    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<GlobalApiResponse> handleBadCredentialsException(BadCredentialsException bce) {

        return new ResponseEntity<>(GlobalApiResponse.builder()
                .message("Username or password not matching")
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .data(null)
                .build(),
                HttpStatus.BAD_REQUEST);

    }

    @Getter
    @Setter
    public static class GlobalExceptionResponse {
        private String status;
        private String httpCode;
        private String message;
        private List<String> errors;

        GlobalExceptionResponse(String status, String httpCode, String message, List<String> errors) {
            this.status = status;
            this.httpCode = httpCode;
            this.message = message;
            this.errors = errors;
        }
    }
}
