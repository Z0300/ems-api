package com.api.ems.registrations;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateRegistrationRequest {
    @NotNull(message = "Event is required")
    @PositiveOrZero(message = "Value must be positive")
    private Long eventId;
}
