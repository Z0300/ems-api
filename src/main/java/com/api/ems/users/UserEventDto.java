package com.api.ems.users;

import com.api.ems.entities.enums.EventStatus;
import com.api.ems.entities.enums.EventType;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    public EventStatus getComputedStatus() {
        LocalDateTime now = LocalDateTime.now();

        if (getEndDateTime().isBefore(now)) return EventStatus.PAST;
        if (getStartDateTime().isBefore(now)) return EventStatus.ONGOING;
        return EventStatus.UPCOMING;
    }

    private EventType type;

    private Boolean isRegistered;
    private Integer totalAttendees;

    public LocalDateTime getStartDateTime() {
        return LocalDateTime.of(eventDate, startTime);
    }

    public LocalDateTime getEndDateTime() {
        return LocalDateTime.of(eventDate, endTime);
    }

}

