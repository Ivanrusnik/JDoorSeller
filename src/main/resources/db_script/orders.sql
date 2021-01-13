DROP TABLE orders CASCADE;
CREATE TABLE orders
(
  order_id        SERIAL PRIMARY KEY,
  company         CHARACTER VARYING(100),
  partner         CHARACTER VARYING(100),
  data            date,
  releasDate      date,
  productionStart int,
  seller          int,
  comment         CHARACTER VARYING(100),
  totalAmount     int,
  totalTax        int,
  totalQuantity   int,
  status          CHARACTER VARYING(100)
);