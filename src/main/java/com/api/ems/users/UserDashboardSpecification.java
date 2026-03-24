package com.api.ems.users;

import com.api.ems.entities.Event;
import com.api.ems.entities.Registration;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class UserDashboardSpecification {


    public static Specification<Event> isPast() {
        return (root, query, cb) ->
                cb.lessThan(root.get("eventDate"), LocalDate.now());
    }

    public static Specification<Event> isRegistered(Long userId) {
        return (root, query, cb) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Registration> subRoot = subquery.from(Registration.class);

            subquery.select(subRoot.get("event").get("id"))
                    .where(cb.equal(subRoot.get("attendee").get("id"), userId));

            return root.get("id").in(subquery);
        };
    }


}
