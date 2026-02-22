package com.api.ems.registrations;

import com.api.ems.entities.Registration;
import com.api.ems.entities.enums.RegistrationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    boolean existsRegistrationsByEventIdAndAttendeeId(Long eventId, Long attendeeId);

    @EntityGraph(attributePaths = {
            "attendee",
            "event",
            "event.organizer"
    })
    @Query("select r from Registration r where r.referenceCode = :referenceCode")
    Optional<Registration> findByReferenceCode(@Param("referenceCode") String referenceCode);

    @EntityGraph(attributePaths = {
            "attendee",
            "event",
            "event.organizer"
    })
    @Query("""
            select r from Registration r\s
                    where (:name is null or lower(r.attendee.fullName)
                             like lower(concat('%', :name, '%')))
                                     and (:status is null or r.status = :status)""")
    Page<Registration> getFilteredRegistrations(Pageable pageable,
                                                @Param("name") String name,
                                                @Param("status") RegistrationStatus status);
}
