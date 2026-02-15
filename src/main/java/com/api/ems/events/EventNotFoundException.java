package com.api.ems.events;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException() {
        super("Event not found.");
    }
}
