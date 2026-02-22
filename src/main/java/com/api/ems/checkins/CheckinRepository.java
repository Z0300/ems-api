package com.api.ems.checkins;

import com.api.ems.entities.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckinRepository extends JpaRepository<Attendance, Long> {
    boolean existsByRegistrationId(Long registrationId);
}
