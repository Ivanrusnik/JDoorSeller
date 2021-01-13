ALTER TABLE door ADD COLUMN doorColor_id int;
UPDATE door SET doorColor_id = null;
