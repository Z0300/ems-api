package com.api.ems.registrations;

import com.api.ems.common.ErrorDto;
import com.api.ems.common.PageDto;
import com.api.ems.entities.enums.RegistrationStatus;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@AllArgsConstructor
@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {
    private final RegistrationService registrationService;

    @GetMapping
    public PageDto<RegistrationDto> getRegistrations(
            @RequestParam(required = false) String name,
            @RequestParam(required = false)RegistrationStatus status,
            @SortDefault(sort = "registrationDate", direction = Sort.Direction.DESC) final Pageable pageable
    ) {
        return registrationService.getRegistrations(pageable, name, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistrationWithEventAndAttendee> getRegistration(@PathVariable Long id) {
        var checkin = registrationService.getRegistration(id);
        return ResponseEntity.ok(checkin);
    }

    @PostMapping
    public ResponseEntity<?> register(
            @Valid @RequestBody CreateRegistrationRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        var result = registrationService.register(request);
        var uri = uriBuilder.path("/api/registrations/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(uri).body(result);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<CancelRegistrationResult> cancelRegistration(
            @PathVariable Long id) {
        var result = registrationService.cancel(id);
        return ResponseEntity.ok(result);
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
