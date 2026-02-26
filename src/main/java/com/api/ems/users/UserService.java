package com.api.ems.users;

import com.api.ems.common.AuthService;
import com.api.ems.common.PageDto;
import com.api.ems.entities.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
    private final AuthService authService;
    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    public UserDto registerUser(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UsernameConflictException();
        }

        var user = userMapper.toEntity(request);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ATTENDEE);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public PageDto<UserDto> getUsers(final Pageable pageable, String name) {
        var page = userRepository.getPagedUser(pageable, name);

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

    public UserDto getUserById(Long id) {

        var user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        return userMapper.toDto(user);
    }

    public UserDto getCurrentUser() {
        var currentUser = authService.getCurrentUser();
        var user = userRepository.findById(currentUser.getId())
                .orElseThrow(UserNotFoundException::new);
        return userMapper.toDto(user);
    }

    public void deleteUser(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

}
