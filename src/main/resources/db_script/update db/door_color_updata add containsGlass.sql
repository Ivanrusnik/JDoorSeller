ALTER TABLE door_colors ADD COLUMN containsGlass int;
UPDATE door_colors SET containsGlass = 0;
