-- Create
INSERT INTO Driver (DriveID, Name, Age, License, Phone)
VALUES(?, ?, ?, ?, ?);

-- Read
SELECT * FROM Driver;
-- Read by ID
SELECT * FROM Driver WHERE DriveID=?;

-- Update
UPDATE Driver
SET Name=?, Age=?, License=?, Phone=?
WHERE DriveID=?;

-- Delete
DELETE FROM Driver WHERE DriveID=?

