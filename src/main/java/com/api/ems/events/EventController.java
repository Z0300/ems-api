package com.api.ems.events;

import com.api.ems.common.ErrorDto;
import com.api.ems.common.PageDto;
import com.api.ems.entities.enums.EventStatus;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public PageDto<EventDto> getEvents(
            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) final Pageable pageable
    ) {
        return eventService.getEvents(pageable);
    }

    @GetMapping("/{eventId}")
    public EventDto getEvent(@PathVariable Long eventId) {
        return eventService.getEvent(eventId);
    }

    @PostMapping
    public ResponseEntity<?> createEvent(
            @Valid @RequestBody CreateEventRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        var eventDto = eventService.createEvent(request);
        var uri = uriBuilder.path("/api/events/{id}").buildAndExpand(eventDto.getId()).toUri();
        return ResponseEntity.created(uri).body(eventDto);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<?> updateEvent(
            @PathVariable Long eventId,
            @Valid @RequestBody UpdateEventRequest request
    ) {
        var eventDto = eventService.updateEvent(request, eventId);
        return ResponseEntity.ok(eventDto);
    }

    @PutMapping("/{eventId}/status/{status}")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long eventId,
            @PathVariable EventStatus status
            ) {
        var eventDto = eventService.updateStatus(eventId, status);
        return ResponseEntity.ok(eventDto);
    }


    @ExceptionHandler(EventTitleConflictException.class)
    public ResponseEntity<ErrorDto> handleTitleConflict(EventTitleConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErrorDto(ex.getMessage())
        );
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorDto> handleEventNotFound(EventNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorDto(ex.getMessage())
        );
    }
}
