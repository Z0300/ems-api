alter table events
    add column type varchar(10) not null default 'ON_SITE' after status