DROP TABLE raw_materials;
CREATE TABLE raw_materials
(
  id                    SERIAL PRIMARY KEY,
  name                  CHARACTER VARYING(100),
  idManufacturerProgram CHARACTER VARYING(100),
  price                 NUMERIC(9, 1)
);