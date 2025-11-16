-- Create
INSERT INTO Payment_Method (PaymentDate, Method)
VALUES (?,?);

-- Read
SELECT * FROM Payment_Method;
-- Read by PaymentID
SELECT *FROM Payment_Method WHERE PaymentID=?;

-- Update
UPDATE Payment_Method
SET PaymentDate = ?, Method=?
WHERE PaymentID = ?;

-- Delete
DELETE FROM Payment_Method WHERE PaymentID = ?;

