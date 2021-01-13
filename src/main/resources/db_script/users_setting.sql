DROP TABLE users_setting;
CREATE TABLE users_setting
(
  id       SERIAL PRIMARY KEY,
  retailmargin    INT,
  salestax        INT,
  includesTax     INT
);