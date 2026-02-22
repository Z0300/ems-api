package com.api.ems.events;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class EventScheduleValidator implements ConstraintValidator<ValidEventSchedule, CreateEventRequest> {
    @Override
    public boolean isValid(CreateEventRequest request, ConstraintValidatorContext context) {
        boolean valid = true;

        if (request.getEventDate() == null || request.getStartTime() == null || request.getEndTime() == null) {
            return true;
        }

        context.disableDefaultConstraintViolation();


        if (!request.getEndTime().isAfter(request.getStartTime())) {
            context.buildConstraintViolationWithTemplate("End time must be after start time")
                    .addPropertyNode("endTime")
                    .addConstraintViolation();
            valid = false;
        }

        LocalDateTime eventStart = LocalDateTime.of(request.getEventDate(), request.getStartTime());
        if (!eventStart.isAfter(LocalDateTime.now())) {
            context.buildConstraintViolationWithTemplate("Event must start in the future")
                    .addPropertyNode("eventDate")
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
