package com.api.ems.users;

import com.api.ems.entities.Event;
import com.api.ems.entities.EventTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserEventMapper {

    @Mapping(target = "tags", expression = "java(mapTags(event.getEventTags()))")
    UserEventDto toDto(Event event);

    default List<String> mapTags(Set<EventTag> eventTags) {
        if (eventTags == null) {
            return null;
        }
        return eventTags.stream()
                .map(eventTag -> eventTag.getTag().getName())
                .collect(Collectors.toList());
    }
}
