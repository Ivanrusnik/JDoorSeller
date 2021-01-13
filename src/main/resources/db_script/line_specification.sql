DROP TABLE line_specification;
create table line_specification
(
  id                  SERIAL PRIMARY KEY,
  doorType_id         INT,
  material_id         CHARACTER VARYING(100),
  name                CHARACTER VARYING(100),
  value               real,
  formula             CHARACTER VARYING(100),
  independent_name    CHARACTER VARYING(100),
  release_operation   CHARACTER VARYING(100),
  write_off_operation CHARACTER VARYING(100),
  specification int
);
