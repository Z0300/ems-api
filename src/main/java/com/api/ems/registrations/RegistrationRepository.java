package com.api.ems.registrations;

import com.api.ems.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    boolean existsRegistrationsByEventIdAndAttendeeId(Long eventId, Long attendeeId);
}
