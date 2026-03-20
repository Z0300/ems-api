package com.api.ems.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event_tags")
public class EventTag {
    @EmbeddedId
    private EventTagId id;

    public EventTag(Long eventId, Long tagId) {
        this.id = new EventTagId(eventId, tagId);
    }
}
