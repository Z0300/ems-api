package com.api.ems.registrations;

import com.api.ems.entities.Registration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {
    @Mapping(source = "event.title", target = "event")
    @Mapping(source = "attendee.fullName", target = "attendee")
    RegistrationDto toDto(Registration registration);

    @Mapping(source = "event", target = "event")
    @Mapping(source = "attendee", target = "attendee")
    RegistrationWithEventAndAttendee toDetailedDto(Registration registration);

    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "attendee.id", target = "attendeeId")
    CreateRegistrationResult toCreateResultDto(Registration registration);

    CancelRegistrationResult toCancelResultDto(Registration registration);

    Registration toEntity(CreateRegistrationRequest request);
}

