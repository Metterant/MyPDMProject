-- Create
INSERT INTO bus_info (PlateNumber, Capacity, DriveID, RouteID)
VALUES(?,?,?,?);

-- Read
SELECT *FROM Bus_info;
-- Read + JOIN driver + Route
SELECT b.BusID, b.PlateNumber, b.Capacity,
       d.Name AS DriverName, r.RouteName
FROM Bus_info b
JOIN Driver d ON b.DriveID = d.DriveID
JOIN Route r ON b.RouteID = r.RouteID;

-- Update
UPDATE Bus_info
SET PlateNumber = ?, Capacity = ?, DriveID = ?, RouteID = ?
WHERE BusID = ?;

-- Delete
DELETE FROM Bus_info WHERE BusID =?

