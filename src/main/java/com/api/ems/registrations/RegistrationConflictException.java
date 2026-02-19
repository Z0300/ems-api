package com.api.ems.registrations;

public class RegistrationConflictException extends RuntimeException {
    public RegistrationConflictException() {
        super("You're already registered to this event.");
    }
}
