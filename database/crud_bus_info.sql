-- Create
INSERT INTO bus_info (PlateNumber, Capacity, DriverID, RouteID)
VALUES(?,?,?,?);

-- Read
SELECT * FROM Bus_info;
-- Read + JOIN driver + Route
SELECT b.BusID, b.PlateNumber, b.Capacity,
       d.Name AS DriverName, r.RouteName
FROM Bus_info b
LEFT JOIN Driver d ON b.DriverID = d.DriverID
LEFT JOIN Route r ON b.RouteID = r.RouteID;

-- Update
UPDATE Bus_info
SET PlateNumber = ?, Capacity = ?, DriverID = ?, RouteID = ?
WHERE BusID = ?;

-- Delete
DELETE FROM Bus_info WHERE BusID =?

