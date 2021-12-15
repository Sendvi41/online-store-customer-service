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
create table baskets(
    id          bigserial
        constraint baskets_pk
            primary key,
    user_id bigserial unique,
    FOREIGN KEY (user_id) REFERENCES users (id)
);
create table items(
    id bigserial
        constraint items_pk
        primary key,
    product_id bigserial not null,
    amount integer not null,
    basket_id bigserial not null,
    FOREIGN KEY (basket_id) REFERENCES baskets(id)
)