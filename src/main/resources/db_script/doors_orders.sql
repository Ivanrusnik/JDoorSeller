DROP TABLE doors_orders CASCADE;
CREATE TABLE doors_orders (
  door_id  int REFERENCES door (door_id) ON UPDATE CASCADE ON DELETE CASCADE,
  order_id int REFERENCES orders (order_id) ON UPDATE CASCADE,
  amount   numeric NOT NULL DEFAULT 1,
  CONSTRAINT door_order_key PRIMARY KEY (door_id, order_id)  -- explicit pk
);
