DROP TABLE Door_Class;
CREATE TABLE Door_Class
(
  Id          SERIAL PRIMARY KEY,
  name        CHARACTER VARYING(100),
  description CHARACTER VARYING(100),
  fireproof   CHARACTER VARYING(100),
  hot         int,
  namePicture CHARACTER VARYING(100)
);