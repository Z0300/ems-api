package com.api.ems.users;

import com.api.ems.common.ErrorDto;
import com.api.ems.common.PageDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public PageDto<UserDto> getUsers(
            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) final Pageable pageable) {
        return userService.getUsers(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getProfile(@PathVariable Long id) {
        var profile = userService.getProfile(id);
        return ResponseEntity.ok(profile);
    }

    @PostMapping
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        var userDto = userService.registerUser(request);
        var uri = uriBuilder.path("/api/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }


    @ExceptionHandler(UsernameConflictException.class)
    public ResponseEntity<ErrorDto> handleUsernameConflict(UsernameConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErrorDto(ex.getMessage())
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFound(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorDto(ex.getMessage())
        );
    }
}
