DROP TABLE Door_Colors;
create table Door_Colors
(
    id                    SERIAL PRIMARY KEY,
    idManufacturerProgram CHARACTER VARYING(12),
    name                  CHARACTER VARYING(100) NOT NULL,
    picturePathFirst      CHARACTER VARYING(100),
    price                 int,
    smooth                int,
    typeOfImage           CHARACTER VARYING(20)  NOT NULL,
    typeOfDoorColor       CHARACTER VARYING(12),
    containsDesign        int,
    containsGlass         int

);
