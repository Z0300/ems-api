package com.api.ems.registrations;

import com.api.ems.entities.enums.RegistrationStatus;
import com.api.ems.events.EventDto;
import com.api.ems.users.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegistrationWithEventAndAttendee {
    private Long id;
    private EventDto event;
    private UserDto attendee;
    private RegistrationStatus status;
    private LocalDateTime registrationDate;
}
