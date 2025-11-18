-- Create
INSERT INTO Driver (Name, Age, License, Phone)
VALUES(?, ?, ?, ?);

-- Read
SELECT * FROM Driver;
-- Read by ID
SELECT * FROM Driver WHERE DriverID=?;

-- Update
UPDATE Driver
SET Name=?, Age=?, License=?, Phone=?
WHERE DriveID=?;

-- Delete
DELETE FROM Driver WHERE DriveID=?

