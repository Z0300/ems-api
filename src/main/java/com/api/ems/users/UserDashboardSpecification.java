package com.api.ems.users;

import com.api.ems.entities.Event;
import com.api.ems.entities.Registration;
import com.api.ems.entities.enums.EventStatus;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class UserDashboardSpecification {

    public static Specification<Event> hasName(String name) {
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("title")),
                        "%" + name.toLowerCase() + "%"
                );
    }

    public static Specification<Event> hasStatus(EventStatus status) {
        return (root, query, cb) ->
                cb.equal(root.get("status"), status);
    }

    public static Specification<Event> isPast() {
        return (root, query, cb) ->
                cb.lessThan(root.get("eventDate"), LocalDate.now());
    }

    public static Specification<Event> withinDays(Integer days) {
        return (root, query, cb) ->
                cb.between(
                        root.get("eventDate"),
                        LocalDate.now().minusDays(days),
                        LocalDate.now()
                );
    }

    public static Specification<Event> isRegistered(Long userId) {
        return (root, query, cb) -> {
            Join<Event, Registration> join = root.join("registrations");
            query.distinct(true);
            return cb.equal(join.get("user").get("id"), userId);
        };
    }
}
