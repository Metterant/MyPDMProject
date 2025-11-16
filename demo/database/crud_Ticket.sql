-- Create (User buys ticket)
INSERT INTO Ticket (Date, UserID, TripID)
VALUES (?, ?, ?);

-- Read
SELECT * FROM Ticket;
-- Read with JOIN
SELECT t.TicketID, t.Date,
       u.UserName,
       tr.DepartureTime, tr.ArrivalTime, tr.Date AS TripDate,
       b.PlateNumber,
       r.RouteName
FROM Ticket t
JOIN Users u ON t.UserID = u.UserID
JOIN Trip tr ON t.TripID = tr.TripID
JOIN Bus_info b ON tr.BusID = b.BusID
JOIN Route r ON tr.RouteID = r.RouteID;

-- Update 
UPDATE Ticket
SET Date = ?, UserID = ?, TripID = ?
WHERE TicketID = ?;

-- Delete
DELETE FROM Ticket WHERE TicketID = ?;

