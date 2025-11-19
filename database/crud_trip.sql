-- Create
INSERT INTO Trip (TripDate, DepartureTime, ArrivalTime, BusID, RouteID)
VALUES (?, ?, ?, ?, ?);

-- Read
SELECT * FROM Trip;

-- Read with JOINs Trip + Bus_info + Route
SELECT t.TripID, t.TripDate, t.DepartureTime, t.ArrivalTime,
       b.PlateNumber, r.RouteName
FROM Trip t
JOIN Bus_info b ON t.BusID = b.BusID
JOIN Route r ON b.RouteID = r.RouteID;

-- Read with JOINs Trip + Bus_info + Route + Driver
SELECT t.TripID, t.TripDate, t.DepartureTime, t.ArrivalTime,
       b.PlateNumber, r.RouteName, d.Name AS DriverName
FROM Trip t
JOIN Bus_info b ON t.BusID = b.BusID
JOIN Route r ON b.RouteID = r.RouteID
JOIN Driver d ON b.DriverID = d.DriverID;

-- Update
UPDATE Trip
SET Date = ?, DepartureTime = ?, ArrivalTime = ?, BusID = ?, RouteID = ?
WHERE TripID = ?;

-- Delete
DELETE FROM Trip WHERE TripID = ?;


