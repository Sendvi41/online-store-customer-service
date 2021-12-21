create table ordered_products
(
    id          bigserial
        constraint orders_products_pk
            primary key,
    product_id bigserial not null,
    amount        integer     NOT NULL,
    price      NUMERIC (18,2) not null,
    order_id    bigserial,
    FOREIGN KEY (product_id) REFERENCES orders (id)
);