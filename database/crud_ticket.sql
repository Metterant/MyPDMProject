-- Create (User buys ticket)
INSERT INTO Ticket (TicketDateTime, UserID, TripID, PaymentID)
VALUES (?, ?, ?, ?);

-- Read
SELECT * FROM Ticket;
-- Read with JOIN
SELECT t.TicketID, t.TicketDateTime,
       u.UserName,
       tr.DepartureTime, tr.ArrivalTime, t.TicketDateTime AS TripDate,
       b.PlateNumber,
       r.RouteName
FROM Ticket t
LEFT  JOIN User u ON t.UserID = u.UserID
LEFT  JOIN Trip tr ON t.TripID = tr.TripID
LEFT  JOIN Bus_info b ON tr.BusID = b.BusID
LEFT  JOIN Route r ON b.RouteID = r.RouteID;

-- Update 
UPDATE Ticket
SET TicketDateTime = ?, UserID = ?, TripID = ?, PaymentID = ?
WHERE TicketID = ?;

-- Delete
DELETE FROM Ticket WHERE TicketID = ?;

