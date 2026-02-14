package com.api.ems.users;

import com.api.ems.common.PageDto;
import com.api.ems.entities.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    public UserDto registerUser(RegisterUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameConflictException();
        }

        var user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ATTENDEE);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public PageDto<UserDto> getUsers(final Pageable pageable) {
        var page = userRepository.findAll(pageable);

        return new PageDto<>(
                page.getContent().stream().map(userMapper::toDto).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    public UserDto getProfile(Long id) {
        var user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }

        return userMapper.toDto(user);
    }
}
