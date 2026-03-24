package com.api.ems.users;

import com.api.ems.common.AuthService;
import com.api.ems.common.PageDto;
import com.api.ems.entities.Event;
import com.api.ems.entities.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final AuthService authService;
    private final UserEventMapper userEventMapper;
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

    public DashboardDto getDashboardEvents() {
        var userId = getCurrentUser().getId();
        Pageable limit = PageRequest.of(0, 5);

        List<UserEventDto> recommended = map(
                dashboardRepository.findRecommended(userId, limit)
        );

        List<UserEventDto> registered = map(
                dashboardRepository.findAll(UserDashboardSpecification.isRegistered(userId), limit)
        );

        List<UserEventDto> past = map(
                dashboardRepository.findAll(UserDashboardSpecification.isPast(), limit)
        );

        List<UserEventDto> popular = map(
                dashboardRepository.findPopular(limit)
        );

        return new DashboardDto(recommended, popular, registered, past);

    }

    private List<UserEventDto> map(Page<Event> page) {
        return page.getContent()
                .stream()
                .map(userEventMapper::toDto)
                .toList();
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
