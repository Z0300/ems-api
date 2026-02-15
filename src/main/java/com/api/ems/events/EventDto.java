package com.api.ems.events;

import com.api.ems.entities.enums.EventStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventDto {
    private int id;
    private String title;
    private String description;
    private LocalDate eventDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private Long capacity;
    private EventStatus status;
}
