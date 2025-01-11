package com.migros.couriertracking.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CourierIdValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCourierId {

    String message() default "Courier ID must contain only numbers";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}