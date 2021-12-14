create table users
(
    id          bigserial
        constraint users_pk
            primary key,
    name         varchar(100)    not null,
    middle_name  varchar(100),
    last_name    varchar(100)    not null,
    birthday     timestamp,
    gender       varchar (10)    not null,
    city         varchar (100),
    phone_number varchar (20),
    email        varchar (50)
);