package com.api.ems.events;


import com.api.ems.entities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDto toDto(Event event);

    @Mapping(target = "type", source = "type")
    Event toEntity(CreateEventRequest request);

    void update(UpdateEventRequest request, @MappingTarget Event event);
}
