package com.api.ems.checkins;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CheckinRequest {
    @NotBlank
    private String referenceCode;
}
