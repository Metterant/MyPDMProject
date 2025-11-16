-- Create
INSERT INTO Payment (Amount, PaymentDATE, UserID, PaymentID)
VALUES (?, ?, ?, ?);

-- Read
SELECT * FROM Payment;

-- Read + JOIN (User + Payment Method)
SELECT p.Amount, p.PaymentDATE,
       u.UserName,
       pm.Method, pm.PaymentDate AS TransactionTime
FROM Payment p
JOIN Users u ON p.UserID = u.UserID
JOIN Payment_Method pm ON p.PaymentID = pm.PaymentID;

-- Update
UPDATE Payment
SET Amount = ?, PaymentDATE = ?, UserID = ?, PaymentID = ?
WHERE PaymentID = ?;

-- Delete
DELETE FROM Payment WHERE PaymentID = ?;

