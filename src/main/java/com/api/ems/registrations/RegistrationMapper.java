package com.api.ems.registrations;

import com.api.ems.entities.Registration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {
    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "attendee.id", target = "attendeeId")
    RegistrationDto toDto(Registration registration);

    Registration toEntity(CreateRegistrationRequest request);
}

