DROP TABLE door_furniture;
create table door_furniture
(
  id                    SERIAL PRIMARY KEY,
  name                  CHARACTER VARYING(100),
  idManufacturerProgram CHARACTER VARYING(12) NOT NULL,
  typeOfFurniture       CHARACTER VARYING(100),
  doorType_id           INT,
  quantity              INT,
  itCylinderLock        INT,
  isTwoSectionLock      INT,
  comment               CHARACTER VARYING(100),
  longKey               INT,
  bugelHandle           INT,
  armorLock             INT,
  picturePathFirst      CHARACTER VARYING(100),
  picturePathSecond     CHARACTER VARYING(100),
  sketchPathFirst       CHARACTER VARYING(100),
  sketchPathSecond      CHARACTER VARYING(100),
  forWarmDoors          INT,
  numberOfDoorLeaves    INT,
  price                 NUMERIC(9, 2),
  pricecomit            CHARACTER VARYING(100)
);