package com.migros.couriertracking.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class CourierIdValidator implements ConstraintValidator<ValidCourierId, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Objects.nonNull(value) && value.matches("\\d+");
    }
}