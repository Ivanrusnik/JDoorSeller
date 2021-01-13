DROP TABLE salary_constants;
create table salary_constants
(
  id    SERIAL PRIMARY KEY,
  name  CHARACTER VARYING(100),
  value NUMERIC(9, 3)
);