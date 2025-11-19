-- Create (User buys ticket)
INSERT INTO Ticket (TicketDateTime, UserID, TripID, PaymentID)
VALUES (?, ?, ?, ?);

-- Read
SELECT * FROM Ticket;
-- Read with JOIN
SELECT t.TicketID, t.TicketDateTime,
       u.UserName,
       tr.DepartureTime, tr.ArrivalTime, tr.TicketDateTime AS TripDate,
       b.PlateNumber,
       r.RouteName
FROM Ticket t
JOIN User u ON t.UserID = u.UserID
JOIN Trip tr ON t.TripID = tr.TripID
JOIN Bus_info b ON tr.BusID = b.BusID
JOIN Route r ON b.RouteID = r.RouteID;

-- Update 
UPDATE Ticket
SET TicketDateTime = ?, UserID = ?, TripID = ?, PaymentID = ?
WHERE TicketID = ?;

-- Delete
DELETE FROM Ticket WHERE TicketID = ?;

