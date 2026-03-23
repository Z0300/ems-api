package com.api.ems.users;

import com.api.ems.entities.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEventMapper {
    UserEventDto toDto(Event event);
}
