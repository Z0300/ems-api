create table tags
(
    id         bigint unsigned auto_increment primary key,
    name       varchar(100) not null unique,
    created_at datetime default current_timestamp
);

create table event_tags
(
    event_id bigint unsigned not null,
    tag_id   bigint unsigned not null,
    primary key (event_id, tag_id),

    constraint fk_event_tags_event
        foreign key (event_id) references events (id)
            on delete cascade,

    constraint fk_event_tags_tag
        foreign key (tag_id) references tags (id)
            on delete cascade
);