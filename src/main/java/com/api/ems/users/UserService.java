package com.api.ems.users;

import com.api.ems.common.AuthService;
import com.api.ems.common.PageDto;
import com.api.ems.entities.Event;
import com.api.ems.entities.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
    private final AuthService authService;
    private final UserEventMapper userEventsMapper;
    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private final DashboardRepository dashboardRepository;

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

    public PageDto<UserEventDto> getUsers(
            final Pageable pageable,
            Boolean registered,
            Boolean past,
            Integer days,
            Long userId) {

        Specification<Event> spec = Specification.where((Specification<Event>) null);

        if (Boolean.TRUE.equals(past)) {
            spec = spec.and(UserDashboardSpecification.isPast());
        }

        if (days != null) {
            spec = spec.and(UserDashboardSpecification.withinDays(days));
        }

        if (Boolean.TRUE.equals(registered) && userId != null) {
            spec = spec.and(UserDashboardSpecification.isRegistered(userId));
        }

        var page = dashboardRepository.findAll(spec, pageable);

        return new PageDto<>(
                page.getContent()
                        .stream()
                        .map(userEventsMapper::toDto)
                        .toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast());
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
