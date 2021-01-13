DROP TABLE glass;
CREATE TABLE glass
(
  id                  SERIAL PRIMARY KEY,
  typeDoorGlass       int,
  toning              int,
  armor               int,
  glassWidth          int,
  glassHeight         int,
  leftGlassPosition   int,
  bottomGlassPosition int
);