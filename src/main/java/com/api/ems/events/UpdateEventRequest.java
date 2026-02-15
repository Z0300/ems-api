package com.api.ems.events;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class UpdateEventRequest {
    @NotBlank(message = "Title is required.")
    @Size(max = 200, message = "Name must be less than 200 characters")
    private String title;

    private String description;

    @NotNull(message = "Event date is required.")
    @FutureOrPresent(message = "Event date must be today or in the future.")
    private LocalDate eventDate;

    @NotNull(message = "Start time is required.")
    private LocalTime startTime;

    @NotNull(message = "End time is required.")
    private LocalTime endTime;

    @NotBlank(message = "Location is required.")
    @Size(max = 255, message = "Name must be less than 200 characters")
    private String location;

    @NotNull(message = "Capacity is required.")
    @PositiveOrZero(message = "Capacity must be a positive number")
    private Long capacity;
}
