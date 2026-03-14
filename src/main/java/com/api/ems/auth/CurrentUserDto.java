package com.api.ems.auth;

import com.api.ems.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentUserDto {
    private Long id;
    private String fullName;
    private String email;
    private Role role;
}
