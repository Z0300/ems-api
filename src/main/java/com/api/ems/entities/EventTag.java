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

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public EventTag(Event event, Tag tag) {
        this.event = event;
        this.tag = tag;
        this.id = new EventTagId(event.getId(), tag.getId());
    }
}
