package com.api.ems.events;


import com.api.ems.entities.Event;
import com.api.ems.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDto toDto(Event event);
    Event toEntity(CreateEventRequest request);
    void update(UpdateEventRequest request, @MappingTarget Event event);
}
