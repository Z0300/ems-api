package com.api.ems.users;

import com.api.ems.common.Lowercase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {
    @NotBlank(message = "Name is required.")
    @Size(max = 150, message = "Name must be less than 150 characters")
    public String fullName;

    @NotBlank(message = "Email is required.")
    @Email(message = "Must be a valid email")
    @Lowercase
    @Size(max = 150, message = "Email must be less than 150 characters")
    public String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 25, message = "Password must be be between 6 to 25 long")
    public String password;

    @NotBlank(message = "Mobile number is required.")
    @Size(max = 15, message = "Password must be less than or equal 15 characters")
    private String mobileNo;
}
