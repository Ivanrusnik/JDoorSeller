DROP TABLE limitation_door;
create table limitation_door
(
  id               SERIAL PRIMARY KEY,
  doorType_id      INT NOT NULL,
  typeSettings     CHARACTER VARYING(100),
  item_id          INT,
  firstItem        CHARACTER VARYING(100),
  secondItem       CHARACTER VARYING(100),
  startRestriction real,
  stopRestriction  real,
  comment          CHARACTER VARYING(100),
  picturePath      CHARACTER VARYING(150),
  step             INT,
  defaultValue     INT,
  pairOfValues     INT NOT NULL,
  cost             INT
);
