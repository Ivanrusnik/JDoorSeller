DROP TABLE specification_setting;
create table specification_setting
(
  id              SERIAL PRIMARY KEY,
  rawMaterials_id int,
  formula_id      int,
  doorType_id     int,
  metal           NUMERIC(9, 1),
  resource        NUMERIC(10, 3)
);