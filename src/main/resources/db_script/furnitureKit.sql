DROP TABLE FurnitureKit;
create table FurnitureKit
(
  id                         SERIAL PRIMARY KEY,
  topLock                    INT,
  topinternaLockDecoration   INT,
  topouterLockDecoration     INT,
  toplockCylinder            INT,
  lowerLock                  INT,
  lowerinternaLockDecoration INT,
  lowerouterLockDecoration   INT,
  lowerlockCylinder          INT,
  handle                     INT,
  closer                     INT,
  endDoorLock                INT,
  nightLock                  INT,
  peephole                   INT,
  amplifierCloser            INT
);