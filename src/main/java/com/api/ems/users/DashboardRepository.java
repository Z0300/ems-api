package com.api.ems.users;

import com.api.ems.entities.Event;
import com.api.ems.entities.EventTag;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface DashboardRepository extends JpaRepository<EventTag, Integer>,
        JpaSpecificationExecutor<Event> {

    @Override
    @NullMarked
    @EntityGraph(attributePaths = {"organizer"})
    Page<Event> findAll(Specification<Event> spec, Pageable pageable);
}
