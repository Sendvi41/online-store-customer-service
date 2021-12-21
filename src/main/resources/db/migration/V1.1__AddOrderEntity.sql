create table orders(
    id          bigserial
        constraint orders_pk
            primary key,
    date        timestamp      NOT NULL,
    status    VARCHAR(50)      NOT NULL,
    user_id bigserial,
    FOREIGN KEY (user_id) REFERENCES users (id)
);