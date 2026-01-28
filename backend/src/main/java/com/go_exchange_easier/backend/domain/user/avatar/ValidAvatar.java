package com.go_exchange_easier.backend.domain.user.avatar;

import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Payload;

@Constraint(validatedBy = AvatarValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAvatar {

    String message() default "Invalid avatar file.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}