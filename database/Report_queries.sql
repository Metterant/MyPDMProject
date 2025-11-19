-- Number of Admins and Passenger
SELECT ur.RoleDescription AS Role, COUNT(*) AS TotalUsers
FROM `User` u
LEFT JOIN UserRoles ur ON u.UserRoleID = ur.UserRoleID
GROUP BY ur.RoleDescription;

-- Average user age of admin and passanger
SELECT ur.RoleDescription AS Role, AVG(u.Age) AS Avg_Age
FROM `User` u
LEFT JOIN UserRoles ur ON u.UserRoleID = ur.UserRoleID
GROUP BY ur.RoleDescription;

-- Most Popular bus route taken(in tickets)
SELECT r.RouteName, COUNT(t.TicketID) AS Tickets_Sold
FROM Ticket t JOIN Trip tr ON t.TripID = tr.TripID
    JOIN Bus_Info b ON b.BusID = tr.BusID
    JOIN Route r ON b.RouteID = r.RouteID
GROUP BY r.RouteName ORDER BY Tickets_Sold DESC LIMIT = ?


-- Total Tickets Sold Per Day
SELECT TicketDateTime, COUNT(*) AS TicketsSold
FROM Ticket
GROUP BY DATE(TicketDateTime)
ORDER BY TicketDateTime DESC;

-- Total Revenue per day
SELECT DATE(PaymentDate) AS PaymentDay, SUM(Amount) AS Total_Revenue
FROM Payment
GROUP BY DATE(PaymentDate)
ORDER BY PaymentDay DESC;

-- Top user who spent the most
SELECT u.UserName, SUM(p.Amount) AS Total_Spent
FROM Payment p
JOIN `User` u ON p.UserID = u.UserID
GROUP BY u.UserName
ORDER BY Total_Spent DESC
LIMIT 5;

-- Total Trips per driver
SELECT d.DriverName, COUNT(t.TripID) AS TotalTrips
FROM Trip t
JOIN Bus_info b ON t.BusID = b.BusID
JOIN Driver d ON b.DriverID = d.DriverID
GROUP BY d.DriverName
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
JOIN Ticket t ON p.PaymentID = t.PaymentID
JOIN Trip tr ON t.TripID = tr.TripID
JOIN Bus_Info b ON tr.BusID = b.BusID
JOIN Route r ON b.RouteID = r.RouteID
GROUP BY r.RouteName;


