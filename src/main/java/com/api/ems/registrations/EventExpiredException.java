package com.api.ems.registrations;

public class EventExpiredException extends RuntimeException {
    public EventExpiredException() {
        super("Registration failed. Event has expired.");
    }
}
