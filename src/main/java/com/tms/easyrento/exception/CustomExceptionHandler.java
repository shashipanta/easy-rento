package com.tms.easyrento.exception;

import com.tms.easyrento.config.CustomMessageSource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/12/23 10:29 PM
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final CustomMessageSource messageSource;

    private static final String PREFIX_UNIQUE = "uk";
    private static final String PREFIX_FOREIGN = "fk";

    private static final String NOT_NULL = "NotNull";
    private static final String NOT_BLANK = "NotBlank";
    private static final String NOT_EMPTY = "NotEmpty";

    /**
     * Customize the handling of {@link MethodArgumentNotValidException}.
     * <p>This method delegates to {@link #handleExceptionInternal}.
     *
     * @param ex      the exception to handle
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} for the response to use, possibly
     * {@code null} when the response is already committed
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        log(ex);
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            validation(errors, error);
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return handleExceptionInternal(ex, genericWithMessage(httpStatus, errors.get(0), errors), headers, httpStatus, request);
//        return new ResponseEntity<>(genericWithMessage(status, errors.get(0), errors ), httpStatus);
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
        String errorMessage;

        String errPrefix = split[0];

        if (PREFIX_UNIQUE.equals(errPrefix)) {
            errorMessage = messageSource.get("exists", messageSource.get(stringConstCode));
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
     *
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
                case NOT_NULL, NOT_BLANK, NOT_EMPTY ->
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
