package com.api.ems.registrations;

public class RegistrationNotFoundException extends RuntimeException {
    public RegistrationNotFoundException() {
        super("Registration not found.");
    }
}
