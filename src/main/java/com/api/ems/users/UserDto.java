package com.api.ems.users;

import com.api.ems.entities.enums.Role;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String fullName;
    private String username;
    private Role role;
}
