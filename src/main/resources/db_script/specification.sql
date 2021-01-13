DROP TABLE specification;
create table specification
(
  id                  SERIAL PRIMARY KEY,
  name                CHARACTER VARYING(100),
  doorType_id         INT NOT NULL
);
