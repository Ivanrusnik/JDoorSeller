DROP TABLE salary_setting;
create table salary_setting (
  id                       SERIAL PRIMARY KEY,
  metal                    NUMERIC(9, 3),
  weldingForOneLeaf        int,
  weldingForTwoLeaf        int,
  weldingForOneLeafMDF     int,
  weldingForTwoLeafMDF     int,
  weldingForOneLeafHot     int,
  weldingForTwoLeafHot     int,
  contactweldingforoneleaf int,
  contactweldingfornwoleaf int
);