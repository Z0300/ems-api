alter table registrations
    add constraint registrations_events_id_fk
        foreign key (event_id) references events (id);

alter table registrations
    add constraint registrations_users_id_fk
        foreign key (attendee_id) references users (id);