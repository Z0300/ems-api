package com.api.ems.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthUserResponse {
    private Long id;
    private String fullName;
    private String email;
}
