package com.tms.easyrento.util.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ConditionalValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionalNotNull {

    String message() default "Invalid operation";

    // The name of the field in the same bean t oread operation type from
    ConditionalValidator.OperationType operationType();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
