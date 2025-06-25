package com.tms.easyrento.util.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-06-25 12:33
 */

public class ConditionalValidator implements ConstraintValidator<ConditionalNotNull, List<MultipartFile>> {

    private OperationType operationType;

    @Override
    public void initialize(ConditionalNotNull constraintAnnotation) {
        this.operationType = constraintAnnotation.operationType();
    }

    @Override
    public boolean isValid(List<MultipartFile> value, ConstraintValidatorContext context) {

        if (operationType == null) {
            return true;
        }

        return switch (operationType) {
            case CREATE, DELETE -> value != null && !value.isEmpty();
            case UPDATE -> value == null || !value.isEmpty();
        };

    }

    public enum OperationType {
        CREATE,
        UPDATE,
        DELETE
    }
}
