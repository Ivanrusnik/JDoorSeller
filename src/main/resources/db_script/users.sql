DROP TABLE users;
CREATE TABLE users
(
  id               SERIAL PRIMARY KEY,
  login            CHARACTER VARYING(100),
  password         CHARACTER VARYING(100),
  discount         int,
  price_group      VARCHAR(255),
  role             VARCHAR(255)
);
