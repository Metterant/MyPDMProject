-- Create
INSERT INTO Route (RouteName, StartLocation, EndLocation, FARE, Distance, Duration)
VALUES (?, ?, ?, ?, ?, ?);

-- Read
SELECT * FROM Route;
-- Read by ID
SELECT * FROM Route WHERE RouteID = ?;

-- Update
UPDATE Route
SET RouteName=?, StartLocation=?, EndLocation=?,
FARE=?, Distance=?, Duration=?
WHERE RouteID=?;

-- Delete
DELETE FROM Route WHERE RouteID= ?
