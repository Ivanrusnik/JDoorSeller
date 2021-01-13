ALTER TABLE users ADD COLUMN role VARCHAR(255);
UPDATE users SET role = USER;
alter table users drop column role;
