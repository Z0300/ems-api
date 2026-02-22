package com.api.ems.events;

import com.api.ems.entities.Event;
import com.api.ems.entities.enums.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByTitle(String title);

    @EntityGraph(attributePaths = "organizer")
    @Query("""
            select e from Event e
            where (:name is null or lower(e.title)
            like lower(concat('%', :name, '%') ) )
            and (:status is null or e.status = :status)""")
    Page<Event> getPageEventWithOrganizer(Pageable pageable, String name, EventStatus status);

    @EntityGraph(attributePaths = "organizer")
    @Query("select e from Event e where e.id = :eventId")
    Optional<Event> getEventWithOrganizerById(@Param("eventId") Long eventId);
}
