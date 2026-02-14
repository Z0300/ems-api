package com.api.ems.users;

public class UsernameConflictException extends RuntimeException {
    public UsernameConflictException() {
        super("Username already taken");
    }
}
