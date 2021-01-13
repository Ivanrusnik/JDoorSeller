DROP TABLE bend_setting;
create table bend_setting
(
  id          SERIAL PRIMARY KEY,
  DoorType_Id int,
  metal       NUMERIC(3, 1),
  sealingline int,
  bend        int,
  guillotine  int
);