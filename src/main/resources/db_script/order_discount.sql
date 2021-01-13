DROP TABLE order_discount;
create table order_discount
(
    id           SERIAL PRIMARY KEY,
    order_id  INT,
    door_id INT,
    discount INT

);