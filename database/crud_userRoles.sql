-- Create
INSERT INTO userRoles(RoleDescription)
VALUES(?);

-- Read
SELECT * FROM userRoles;
-- Read by ID
SELECT * FROM userRoles WHERE UserRoleID =?;

-- Update
UPDATE userRoles
SET RoleDescription=?
WHERE UserRoleID=?;

-- Delete 
DELETE FROM userRoles
WHERE UserRoleID=?