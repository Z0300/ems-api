package com.api.ems.events;

public class EventTitleConflictException extends RuntimeException {
    public EventTitleConflictException() {
        super("Title already exists");
    }
}
