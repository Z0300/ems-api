create table users
(
    id            bigint unsigned auto_increment primary key,
    full_name     varchar(150)                          not null,
    email         varchar(150)                          not null,
    password_hash varchar(255)                          not null,
    role          varchar(25) default 'ATTENDEE'        not null,
    mobile_no     varchar(15)                           not null,
    created_at    datetime    default current_timestamp not null
);
create table events
(
    id           bigint unsigned auto_increment primary key,
    title        varchar(200)                          not null,
    description  text                                  null,
    event_date   date                                  not null,
    start_time   time                                  not null,
    end_time     time                                  not null,
    location     varchar(255)                          not null,
    capacity     int unsigned                          not null,
    organizer_id bigint unsigned                       not null,
    status       varchar(10) default 'OPEN'            not null,
    created_at   datetime    default current_timestamp not null,
    constraint events_users_fk foreign key (organizer_id) references users (id) on delete restrict
);
create table registrations
(
    id                bigint unsigned auto_increment primary key,
    event_id          bigint unsigned                       not null,
    attendee_id       bigint unsigned                       not null,
    registration_date datetime    default current_timestamp not null,
    reference_code    varchar(10)                           not null,
    status            varchar(20) default 'REGISTERED'      not null,
    constraint registrations_events_fk foreign key (event_id) references events (id) on delete cascade,
    constraint registrations_users_fk foreign key (attendee_id) references users (id) on delete restrict
);
create table attendance
(
    id              bigint unsigned auto_increment primary key,
    registration_id bigint unsigned                    not null,
    check_in_time   datetime default current_timestamp not null,
    scanned_by      bigint unsigned                    null,
    constraint attendance_registrations_fk foreign key (registration_id) references registrations (id) on delete cascade,
    constraint attendance_users_fk foreign key (scanned_by) references users (id) on delete set null
);

CREATE UNIQUE INDEX uq_users_email ON users (email);
CREATE INDEX users_role_idx ON users (role);
CREATE INDEX events_date_idx ON events (event_date);
CREATE INDEX events_status_idx ON events (status);
CREATE UNIQUE INDEX event_attendee_uq ON registrations (event_id, attendee_id);
CREATE UNIQUE INDEX registrations_uq ON registrations (reference_code);
CREATE UNIQUE INDEX attendance_registrations_uq ON attendance (registration_id);