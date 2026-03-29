package com.api.ems.users;

import com.api.ems.entities.Event;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface DashboardRepository extends JpaRepository<Event, Integer>,
        JpaSpecificationExecutor<Event> {

    @Override
    @NullMarked
    @EntityGraph(attributePaths = {"organizer"})
    Page<Event> findAll(Specification<Event> spec, Pageable pageable);

    @Query("""
            select e from Event e
                     where e.eventDate >= CURRENT_DATE
                       and e.status = com.api.ems.entities.enums.EventStatus.OPEN
                       and e.id not in (
                           select r.event.id from Registration r where r.attendee.id = :userId
                       )
                       and exists (
                           select 1 from e.eventTags et
                           where et.tag.id in (
                               select t2.id from Registration r
                               join r.event e2
                               join e2.eventTags et2
                               join et2.tag t2
                               where r.attendee.id = :userId
                           )
                       )
                     order by
                       (select count(et4) from e.eventTags et4\s
                        where et4.tag.id in (
                            select t2.id from Registration r
                            join r.event e2
                            join e2.eventTags et2
                            join et2.tag t2
                            where r.attendee.id = :userId
                        )
                       ) desc,
                       (select count(r4) from Registration r4 where r4.event.id = e.id) desc
            """)
    @EntityGraph(attributePaths = {"organizer"})
    Page<Event> findRecommended(Long userId, Pageable pageable);

    @Query("""
            select e from Event e
            where e.eventDate >= CURRENT_DATE
            and e.status = com.api.ems.entities.enums.EventStatus.OPEN
            order by (
                select count(r) from Registration r where r.event.id = e.id
            ) desc
            """)
    @EntityGraph(attributePaths = {"organizer"})
    Page<Event> findPopular(Pageable pageable);
}
