ALTER TABLE door_colors ADD COLUMN containsDesign int;
UPDATE door_colors SET containsDesign = 0;
