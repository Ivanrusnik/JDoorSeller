DROP TABLE door CASCADE;
CREATE TABLE door
(
  door_id SERIAL PRIMARY KEY,
  name CHARACTER VARYING(100),
  doorType int,
  widthDoor int,
  heightDoor int,
  аctivDoorLeafWidth int,
  doorFanlightHeight int,
  metal int,
  deepnessDoor int,
  thicknessDoorLeaf int,
  sideDoorOpen CHARACTER VARYING(100),
  innerDoorOpen CHARACTER VARYING(100),
  doorstep int,
  stainlessSteelDoorstep int,
  topDoorTrim int,
  leftDoorTrim int,
  rightDoorTrim int,
  price int,
  doorColor CHARACTER VARYING(100)
);