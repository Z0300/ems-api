package com.api.ems.checkins;

public class CheckinNotFoundException extends RuntimeException {
    public CheckinNotFoundException() {
        super("Checkin not found");
    }
}
