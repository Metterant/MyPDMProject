-- Create
INSERT INTO user(Username, Age, Phone, Address, Role)
VALUES(?,?,?,?,?);

-- Read
SELECT *FROM user;
-- Read by ID
SELECT *FROM user WHERE UserID =?;

-- Update
UPDATE user
SET Phone=?, Address=?
WHERE UserID=?;

-- Delete 
DELETE FROM user
WHERE UserID=?
