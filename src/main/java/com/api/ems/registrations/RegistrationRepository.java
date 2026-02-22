package com.api.ems.registrations;

import com.api.ems.entities.Registration;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    boolean existsRegistrationsByEventIdAndAttendeeId(Long eventId, Long attendeeId);

    @EntityGraph(attributePaths = "event")
    @Query("select r from Registration r where r.referenceCode = :referenceCode")
    Optional<Registration> findByReferenceCode(@Param("referenceCode") String referenceCode);
}
