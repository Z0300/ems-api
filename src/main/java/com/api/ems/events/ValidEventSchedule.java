package com.api.ems.events;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EventScheduleValidator.class)
public @interface ValidEventSchedule {
    String message() default "Invalid event schedule.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
