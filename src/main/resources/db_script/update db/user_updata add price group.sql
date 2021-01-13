ALTER TABLE users ADD COLUMN price_group VARCHAR(255);
UPDATE users SET price_group = 'RETAIL_PRICE';
