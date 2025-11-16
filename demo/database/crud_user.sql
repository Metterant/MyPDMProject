-- Create
INSERT INTO Users(UserName, Age, Phone, Address, Role)
VALUES(?,?,?,?,?);

-- Read
SELECT *FROM Users;
-- Read by ID
SELECT *FROM Users WHERE UserID =?;

-- Update
UPDATE Users
SET Phone=?, Address=?
WHERE UserID=?;

-- Delete 
DELETE FROM Users
WHERE UserID=?
