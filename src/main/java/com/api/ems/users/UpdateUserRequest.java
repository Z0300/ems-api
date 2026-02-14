package com.api.ems.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @NotBlank(message = "Name is required.")
    @Size(max = 150, message = "Name must be less than 150 characters")
    public String fullName;
}
