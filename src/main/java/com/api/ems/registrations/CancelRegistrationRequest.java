package com.api.ems.registrations;

import com.api.ems.entities.enums.RegistrationStatus;
import lombok.Data;

@Data
public class CancelRegistrationRequest {
    private RegistrationStatus status;
}
