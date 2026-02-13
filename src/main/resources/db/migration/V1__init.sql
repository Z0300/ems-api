create table users
(
    id            bigint unsigned auto_increment
        primary key,
    full_name     varchar(150)                          not null,
    username      varchar(150)                          not null,
    password_hash varchar(255)                          not null,
    role          varchar(25) default 'USER'            not null,
    created_at    datetime    default current_timestamp not null,
    constraint uq_users_username
        unique (username)
);

create table events
(
    id           bigint unsigned auto_increment
        primary key,
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
    constraint events_users_id_fk
        foreign key (organizer_id) references users (id)
            on delete cascade
);

create table registrations
(
    id                bigint unsigned auto_increment
        primary key,
    event_id          bigint unsigned                       not null,
    attendee_id       bigint unsigned                       not null,
    registration_date datetime    default current_timestamp not null,
    qr_token          varchar(255)                          not null,
    status            varchar(20) default 'REGISTERED'      not null,
    constraint event_attendee_uq
        unique (event_id, attendee_id),
    constraint qr_token_uq
        unique (qr_token)
);

create table attendance
(
    id              bigint unsigned auto_increment
        primary key,
    registration_id bigint unsigned                    not null,
    check_in_time   datetime default current_timestamp not null,
    scanned_by      bigint unsigned                    not null,
    constraint attendance_registrations_uq
        unique (registration_id),
    constraint attendance_registrations_fk
        foreign key (registration_id) references registrations (id)
            on delete cascade,
    constraint attendance_users_fk
        foreign key (scanned_by) references users (id)
);


create index users_role_idx
    on users (role);

create index events_organizer_idx
    on events (organizer_id);

create index events_date_idx
    on events (event_date);

create index events_status_idx
    on events (status);

create index registrations_attendee_idx
    on registrations (attendee_id);

create index registrations_event_idx
    on registrations (event_id);

create index attendance_check_in_time_idx
    on attendance (check_in_time);

create index attendance_scanned_by_idx
    on attendance (scanned_by);




