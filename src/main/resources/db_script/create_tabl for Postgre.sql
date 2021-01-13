

create table Size_Door_Parts
(
  id          SERIAL PRIMARY KEY,
  doorType_id INT,
  name        CHARACTER VARYING(100) NOT NULL,
  condition   CHARACTER VARYING(100),
  width       CHARACTER VARYING(100) NOT NULL,
  height      CHARACTER VARYING(100) NOT NULL,
  quantity    CHARACTER VARYING(100) NOT NULL
);




