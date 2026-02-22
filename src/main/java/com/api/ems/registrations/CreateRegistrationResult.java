package com.api.ems.registrations;

import lombok.Data;

@Data
public class CreateRegistrationResult {
    private Long id;
    private Long eventId;
    private Long attendeeId;
    private String base64Image;
}
