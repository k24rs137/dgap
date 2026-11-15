ALTER TABLE users ADD COLUMN display_name VARCHAR(40);

UPDATE users
SET display_name = user_id
WHERE display_name IS NULL;

ALTER TABLE users ALTER COLUMN display_name SET NOT NULL;
