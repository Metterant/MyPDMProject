-- Create
INSERT INTO Trip (Date, DepartureTime, ArrivalTime, BusID, RouteID)
VALUES (?, ?, ?, ?, ?);

-- Read
SELECT * FROM Trip;

-- Read with JOINs Trip + Bus_info + Route
SELECT t.TripID, t.Date, t.DepartureTime, t.ArrivalTime,
       b.PlateNumber, r.RouteName
FROM Trip t
JOIN Bus_info b ON t.BusID = b.BusID
JOIN Route r ON t.RouteID = r.RouteID;

-- Update
UPDATE Trip
SET Date = ?, DepartureTime = ?, ArrivalTime = ?, BusID = ?, RouteID = ?
WHERE TripID = ?;

-- Delete
DELETE FROM Trip WHERE TripID = ?;


