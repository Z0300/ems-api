package com.api.ems.users;

import com.api.ems.entities.enums.EventStatus;
import com.api.ems.entities.enums.EventType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class UserEventDto {
    private int id;
    private String title;
    private String description;
    private LocalDate eventDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private Long capacity;

    @JsonIgnore
    private EventType type;

    @JsonProperty("type")
    public String getComputedType() {
        return type != null ? type.getLabel() : null;
    }

    private List<String> tags;

    public LocalDateTime getStartDateTime() {
        return (eventDate == null || startTime == null)
                ? null
                : LocalDateTime.of(eventDate, startTime);
    }

    public LocalDateTime getEndDateTime() {
        return (eventDate == null || endTime == null)
                ? null
                : LocalDateTime.of(eventDate, endTime);
    }

    @JsonProperty("status")
    public EventStatus getComputedStatus() {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime start = getStartDateTime();
        LocalDateTime end = getEndDateTime();

        if (start == null || end == null) return EventStatus.UPCOMING;

        if (end.isBefore(now)) return EventStatus.PAST;
        if (start.isBefore(now)) return EventStatus.ONGOING;
        return EventStatus.UPCOMING;
    }
}

