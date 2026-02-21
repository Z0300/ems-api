package com.api.ems.registrations;

public class ReferenceCodeCollisionException extends RuntimeException {
    public ReferenceCodeCollisionException() {
        super("Failed to generate unique reference code after retries");
    }
}
