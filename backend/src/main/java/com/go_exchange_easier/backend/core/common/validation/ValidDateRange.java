package com.go_exchange_easier.backend.core.common.validation;

import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = DateRangeValidator.class)
public @interface ValidDateRange {

    String message() default "Start date must be before end date.";
    String startDateField() default "startDate";
    String endDateField() default "endDate";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
