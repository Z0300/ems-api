package com.api.ems.common;

import com.api.ems.entities.User;
import com.api.ems.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long) Objects.requireNonNull(authentication).getPrincipal();
        return userRepository.findById(Objects.requireNonNull(userId)).orElse(null);
    }
}