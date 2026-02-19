package com.api.ems.registrations;

import com.api.ems.common.AuthService;
import com.api.ems.common.QrCodeGenerator;
import com.api.ems.common.QrCodeTokenService;
import com.api.ems.entities.Event;
import com.api.ems.entities.enums.RegistrationStatus;
import com.api.ems.events.EventNotFoundException;
import com.api.ems.events.EventRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final AuthService authService;
    private final RegistrationMapper registrationMapper;
    private final QrCodeTokenService qrCodeTokenService;
    private final QrCodeGenerator qrCodeGenerator;
    private final EventRepository eventRepository;

    public RegistrationDto register(CreateRegistrationRequest request) {
        var attendee = authService.getCurrentUser();

        var event = getEventById(request.getEventId());

        if (registrationRepository.existsRegistrationsByEventIdAndAttendeeId(request.getEventId(), attendee.getId())) {
            throw new RegistrationConflictException();
        }

        if (event.isExpired()) {
            throw new EventExpiredException();
        }

        var registration = registrationMapper.toEntity(request);
        var qrCodeToken = getQrToken(request.getEventId());
        registration.setEvent(event);
        registration.setAttendee(attendee);
        registration.setQrToken(qrCodeToken);
        registration.setStatus(RegistrationStatus.REGISTERED);

        registrationRepository.save(registration);

        return registrationMapper.toDto(registration);
    }


    private String getQrToken(
            Long eventId) {

        var event = getEventById(eventId);

        return qrCodeTokenService.generateToken(
                eventId,
                event.getEventDate(),
                event.getStartTime(),
                event.getEndTime());
    }

    private byte[] createClientQrCode(
            Long eventId) {

        var event = getEventById(eventId);

        String token = qrCodeTokenService.generateToken(
                eventId,
                event.getEventDate(),
                event.getStartTime(),
                event.getEndTime());

        return qrCodeGenerator.generateQrImage(token, 300, 300);
    }

    private @NonNull Event getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(EventNotFoundException::new);
    }
}
