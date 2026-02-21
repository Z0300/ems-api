package com.api.ems.registrations;

import com.api.ems.common.ErrorDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@AllArgsConstructor
@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<?> register(
            @Valid @RequestBody CreateRegistrationRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        var eventDto = registrationService.register(request);
        var uri = uriBuilder.path("/api/registrations/{id}").buildAndExpand(eventDto.getId()).toUri();
        return ResponseEntity.created(uri).body(eventDto);
    }

    @ExceptionHandler(RegistrationConflictException.class)
    public ResponseEntity<ErrorDto> handleDuplicateRegistration(RegistrationConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErrorDto(ex.getMessage())
        );
    }

    @ExceptionHandler(EventExpiredException.class)
    public ResponseEntity<ErrorDto> handleExpiredEvent(EventExpiredException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorDto(ex.getMessage())
        );
    }

    @ExceptionHandler(ReferenceCodeCollisionException.class)
    public ResponseEntity<ErrorDto> handleReferenceCodeCollision(ReferenceCodeCollisionException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErrorDto(ex.getMessage())
        );
    }
}
