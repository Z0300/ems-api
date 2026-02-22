package com.api.ems.registrations;

import com.api.ems.common.AuthService;
import com.api.ems.common.PageDto;
import com.api.ems.common.QrCodeGenerator;
import com.api.ems.entities.Registration;
import com.api.ems.entities.enums.RegistrationStatus;
import com.api.ems.events.EventNotFoundException;
import com.api.ems.events.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public PageDto<RegistrationDto> getRegistrations(final Pageable pageable, String name, RegistrationStatus status) {

        Page<Registration> page = registrationRepository.getFilteredRegistrations(pageable, name, status);

        return new PageDto<>(
                page.getContent().stream().map(registrationMapper::toDto).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    public RegistrationWithEventAndAttendee getRegistration(Long id) {
        var checkin = registrationRepository.findById(id)
                .orElseThrow(RegistrationNotFoundException::new);

        return registrationMapper.toDetailedDto(checkin);
    }


    @Transactional
    public CreateRegistrationResult register(CreateRegistrationRequest request) {

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

                var dto = registrationMapper.toCreateResultDto(registration);
                dto.setBase64Image(qrBase64);

                return dto;

            } catch (DataIntegrityViolationException ex) {
                attempts++;
            }
        }
        throw new ReferenceCodeCollisionException();
    }

    public CancelRegistrationResult cancel(Long id) {
        var registration = registrationRepository.findById(id)
                .orElseThrow(RegistrationNotFoundException::new);

        registration.setStatus(RegistrationStatus.CANCELLED);
        registrationRepository.save(registration);

        return registrationMapper.toCancelResultDto(registration);
    }
}
