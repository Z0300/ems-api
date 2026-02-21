package com.api.ems.registrations;

import com.api.ems.common.AuthService;
import com.api.ems.common.QrCodeGenerator;
import com.api.ems.entities.enums.RegistrationStatus;
import com.api.ems.events.EventNotFoundException;
import com.api.ems.events.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@AllArgsConstructor
@Service
public class RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final AuthService authService;
    private final RegistrationMapper registrationMapper;
    private final EventRepository eventRepository;
    private final QrCodeGenerator qrCodeGenerator;
    private final ShortCodeGenerator shortCodeGenerator;

    @Transactional
    public RegistrationDto register(CreateRegistrationRequest request) {

        var attendee = authService.getCurrentUser();

        var event = eventRepository.findById(request.getEventId())
                .orElseThrow(EventNotFoundException::new);

        if (registrationRepository
                .existsRegistrationsByEventIdAndAttendeeId(
                        request.getEventId(), attendee.getId())) {
            throw new RegistrationConflictException();
        }

        if (event.isExpired()) {
            throw new EventExpiredException();
        }

        int attempts = 0;

        while (attempts < 5) {

            try {
                var registration = registrationMapper.toEntity(request);

                registration.setEvent(event);
                registration.setAttendee(attendee);
                registration.setStatus(RegistrationStatus.REGISTERED);

                String referenceCode = shortCodeGenerator.generate();
                registration.setReferenceCode(referenceCode);
                registrationRepository.save(registration);

                byte[] qrImage = qrCodeGenerator.generateQrImage(referenceCode, 200, 200);
                String qrBase64 = Base64.getEncoder().encodeToString(qrImage);

                var dto = registrationMapper.toDto(registration);
                dto.setBase64Image(qrBase64);

                return dto;

            } catch (DataIntegrityViolationException ex) {
                attempts++;
            }
        }
        throw new ReferenceCodeCollisionException();
    }
}
