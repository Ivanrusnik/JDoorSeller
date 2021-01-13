DROP TABLE material_formula;
CREATE TABLE material_formula
(
  id                    SERIAL PRIMARY KEY,
  idManufacturerProgram CHARACTER VARYING(100),
  name                  CHARACTER VARYING(100),
  condition1            CHARACTER VARYING(100),
  calculationFormula1   CHARACTER VARYING(100),
  condition2            CHARACTER VARYING(100),
  calculationFormula2   CHARACTER VARYING(100),
  condition3            CHARACTER VARYING(100),
  calculationFormula3   CHARACTER VARYING(100)
);