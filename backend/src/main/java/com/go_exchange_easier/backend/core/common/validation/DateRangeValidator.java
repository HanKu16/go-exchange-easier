package com.go_exchange_easier.backend.core.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {

    private String startDateField;
    private String endDateField;

    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
        this.startDateField = constraintAnnotation.startDateField();
        this.endDateField = constraintAnnotation.endDateField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        LocalDate startDate;
        LocalDate endDate;

        if (value == null) {
            return true;
        }
        try {
            try {
                startDate = getDateFromRecord(value, startDateField);
                endDate = getDateFromRecord(value, endDateField);
            } catch (NoSuchMethodException e) {
                startDate = getDateFromClass(value, startDateField);
                endDate = getDateFromClass(value, endDateField);
            }
            if ((startDate == null) || (endDate == null)) {
                return true;
            }
            return startDate.isBefore(endDate);
        } catch (Exception e) {
            return false;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private LocalDate getDateFromClass(Object value, String dateAsText)
            throws NoSuchMethodException, InvocationTargetException,
                IllegalAccessException {
        return (LocalDate) value.getClass()
                .getMethod("get" + capitalize(dateAsText))
                .invoke(value);
    }

    private LocalDate getDateFromRecord(Object value, String dateAsText)
            throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        return (LocalDate) value.getClass()
                .getMethod(dateAsText)
                .invoke(value);
    }

}
