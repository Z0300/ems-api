package com.api.ems.registrations;

import lombok.Data;

@Data
public class RegistrationDto {
    private Long id;
    private Long eventId;
    private Long attendeeId;
    private String qrToken;
    private String status;
}
