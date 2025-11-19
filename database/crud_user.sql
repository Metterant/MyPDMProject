-- Create
INSERT INTO user(Username, UserPassword, FullName, Age, Phone, UserAddress, UserRoleID)
VALUES(?,?,?,?,?,?,?);

-- Read
SELECT * FROM user;
-- Read by ID
SELECT * FROM user WHERE UserID =?;

-- Update
UPDATE user
SET Phone=?, UserAddress=?
WHERE UserID=?;

-- Delete 
DELETE FROM user
WHERE UserID=?
