package com.api.ems.checkins;

import com.api.ems.common.AuthService;
import com.api.ems.common.PageDto;
import com.api.ems.registrations.RegistrationNotFoundException;
import com.api.ems.registrations.RegistrationRepository;
import com.api.ems.users.UserDto;
import com.api.ems.users.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CheckinService {

    private final CheckinMapper checkinMapper;
    private final AuthService authService;
    private final RegistrationRepository registrationRepository;
    private final CheckinRepository checkinRepository;

    public PageDto<CheckinDto> getCheckins(final Pageable pageable) {
        var page = checkinRepository.findAll(pageable);

        return new PageDto<>(
                page.getContent().stream().map(checkinMapper::toDto).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    public CheckinWithAttendeeAndScannedByDto getCheckin(Long id) {
        var checkin = checkinRepository.findById(id)
                .orElseThrow(CheckinNotFoundException::new);

        return checkinMapper.toDetailedDto(checkin);
    }

    public CheckinResult checkin(CheckinRequest request) {
        var checkin = checkinMapper.toEntity(request);
        var scannedBy = authService.getCurrentUser();

        var registration = registrationRepository
                .findByReferenceCode(request.getReferenceCode())
                .orElseThrow(RegistrationNotFoundException::new);

        if (checkinRepository.existsByRegistrationId(registration.getId())) {
            throw new CheckinConflictException();
        }

        checkin.setRegistration(registration);
        checkin.setScannedBy(scannedBy);
        checkinRepository.save(checkin);

        return checkinMapper.toCheckinResultDto(checkin);
    }
}
