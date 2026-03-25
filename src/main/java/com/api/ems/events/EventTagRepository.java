package com.api.ems.events;

import com.api.ems.entities.EventTag;
import com.api.ems.entities.EventTagId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTagRepository extends JpaRepository<EventTag, EventTagId> {
}
