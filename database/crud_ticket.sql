-- Create (User buys ticket)
INSERT INTO Ticket (TicketDate, UserID, TripID)
VALUES (?, ?, ?);

-- Read
SELECT * FROM Ticket;
-- Read with JOIN
SELECT t.TicketID, t.TicketDate,
       u.UserName,
       tr.DepartureTime, tr.ArrivalTime, tr.TicketDate AS TripDate,
       b.PlateNumber,
       r.RouteName
FROM Ticket t
JOIN User u ON t.UserID = u.UserID
JOIN Trip tr ON t.TripID = tr.TripID
JOIN Bus_info b ON tr.BusID = b.BusID
JOIN Route r ON b.RouteID = r.RouteID;

-- Update 
UPDATE Ticket
SET TicketDate = ?, UserID = ?, TripID = ?
WHERE TicketID = ?;

-- Delete
DELETE FROM Ticket WHERE TicketID = ?;

