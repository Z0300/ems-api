package com.api.ems.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {
    @NotBlank(message = "Name is required.")
    @Size(max = 150, message = "Name must be less than 150 characters")
    public String fullName;

    @NotBlank(message = "Username is required.")
    @Size(max = 150, message = "Username must be less than 150 characters")
    public String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 25, message = "Password must be be between 6 to 25 long")
    public String password;
}
