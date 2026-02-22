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
            @RequestParam(required = false) String name,
            @RequestParam(required = false) EventStatus status,
            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) final Pageable pageable
    ) {
        return eventService.getEvents(pageable, name, status);
    }

    @GetMapping("/{id}")
    public EventDto getEvent(@PathVariable Long id) {
        return eventService.getEvent(id);
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEventRequest request
    ) {
        var eventDto = eventService.updateEvent(request, id);
        return ResponseEntity.ok(eventDto);
    }

    @PatchMapping("/{id}/cancel}")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        var eventDto = eventService.cancel(id);
        return ResponseEntity.ok(eventDto);
    }

    @ExceptionHandler(EventTitleConflictException.class)
    public ResponseEntity<ErrorDto> handleTitleConflict(EventTitleConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErrorDto(ex.getMessage())
        );
    }


}
