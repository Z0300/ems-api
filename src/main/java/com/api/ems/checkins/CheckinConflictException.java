package com.api.ems.checkins;

public class CheckinConflictException extends RuntimeException {
    public CheckinConflictException() {
        super("Already checked in");
    }
}
