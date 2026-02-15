package com.api.ems.events;

import com.api.ems.common.AuthService;
import com.api.ems.common.PageDto;
import com.api.ems.entities.enums.EventStatus;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final AuthService authService;

    public EventDto createEvent(CreateEventRequest request) {
        existsTitleFromDb(request.getTitle());
        var event = eventMapper.toEntity(request);

        var organizer = authService.getCurrentUser();

        event.setOrganizer(organizer);
        event.setStatus(EventStatus.OPEN);
        eventRepository.save(event);

        return eventMapper.toDto(event);
    }

    public EventDto updateEvent(UpdateEventRequest request, Long eventId) {
        existsTitleFromDb(request.getTitle());

        var event = eventRepository.findById(eventId).orElse(null);
        if (event == null) {
            throw new EventNotFoundException();
        }
        eventMapper.update(request, event);
        eventRepository.save(event);

        return eventMapper.toDto(event);
    }

    public EventDto updateStatus(Long eventId, EventStatus status) {
        var event = eventRepository.findById(eventId).orElse(null);
        if (event == null) {
            throw new EventNotFoundException();
        }
        event.setStatus(status);
        eventRepository.save(event);

        return eventMapper.toDto(event);
    }


    public PageDto<EventDto> getEvents(final Pageable pageable) {
        var page = eventRepository.getPageEventWithOrganizer(pageable);

        return new PageDto<>(
                page.getContent().stream().map(eventMapper::toDto).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    public EventDto getEvent(Long eventId) {
        var event = eventRepository
                .getEventWithOrganizerById(eventId)
                .orElseThrow(EventNotFoundException::new);

        var organizer = authService.getCurrentUser();

        if (!event.isOrganizedBy(organizer)) {
            throw new AccessDeniedException("You don't have access to this event");
        }

        return eventMapper.toDto(event);
    }


    private void existsTitleFromDb(String request) {
        if (eventRepository.existsByTitle(request)) {
            throw new EventTitleConflictException();
        }
    }
}
