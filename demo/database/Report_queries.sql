-- Number of Admins and Passenger
SELECT UserRole, COUNT(*) AS TotalUsers
FROM user
GROUP BY UserRole;

-- Average user age of admin and passanger
SELECT UserRole, AVG(Age) AS Avg_Age
FROM user
GROUP BY UserRole;

-- Most Popular bus route taken(in tickets)
SELECT r.RouteName, COUNT(t.TicketID) AS Tickets_Sold
FROM Ticket t
JOIN Trip tr ON t.TripID = tr.TripID
JOIN Route r ON tr.RouteID = r.RouteID
GROUP BY r.RouteName
ORDER BY Tickets_Sold DESC;

-- Total Tickets Sold Per Day
SELECT DATE(Date) AS TicketDate, COUNT(*) AS TicketsSold
FROM Ticket
GROUP BY DATE(Date)
ORDER BY TicketDate DESC;

-- Total Revenue per day
SELECT PaymentDATE AS DATE, SUM(Amount) AS Total_Revenue
FROM payment
GROUP BY PaymentDATE
ORDER BY Date DESC;

-- Top user who spent the most
SELECT u.UserName,  SUM(p.Amount) AS Total_Spent
FROM Payment p
JOIN user u ON p.UserID = u.UserID
GROUP BY u.UserName
ORDER BY Total_Spent DESC
LIMIT 5;

-- Total Trips per driver
SELECT d.Name AS DriverName, COUNT(t.TripID) AS TotalTrips
FROM Trip t
JOIN Bus_info b ON t.BusID = b.BusID
JOIN Driver d ON b.DriveID = d.DriveID
GROUP BY d.Name
ORDER BY TotalTrips DESC;

-- Bus usage rate
SELECT b.PlateNumber, COUNT(t.TripID) AS TripCount
FROM Bus_info b
LEFT JOIN Trip t ON b.BusID = t.BusID
GROUP BY b.PlateNumber
ORDER BY TripCount DESC;

-- Average revenue per route
SELECT r.RouteName, AVG(p.Amount) AS AvgFare
FROM Payment p
JOIN Ticket t ON p.UserID = t.UserID
JOIN Trip tr ON t.TripID = tr.TripID
JOIN Route r ON tr.RouteID = r.RouteID
GROUP BY r.RouteName;


