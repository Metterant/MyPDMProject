-- Create
INSERT INTO Driver (DriverName, Age, License, Phone)
VALUES(?, ?, ?, ?);

-- Read
SELECT * FROM Driver;
-- Read by ID
SELECT * FROM Driver WHERE DriverID=?;

-- Update
UPDATE Driver
SET DriverName=?, Age=?, License=?, Phone=?
WHERE DriverID=?;

-- Delete
DELETE FROM Driver WHERE DriverID=?

