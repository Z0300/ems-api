package com.api.ems.registrations;

public class QrGenerationException extends RuntimeException {
    public QrGenerationException() {
        super("Failed to generate QR image");
    }
}
