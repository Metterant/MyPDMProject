package com.buspass.queries;

import java.util.List;
import java.util.Map;

import com.buspass.db.QueryExecutionModule;

public class ReportQuery {
    
	public List<Map<String, Object>> getUserCountsByRole() {
		String sql = "SELECT ur.RoleDescription AS Role, COUNT(*) AS TotalUsers "
				   + "FROM `User` u LEFT JOIN UserRoles ur ON u.UserRoleID = ur.UserRoleID "
				   + "GROUP BY ur.RoleDescription";
		return QueryExecutionModule.executeQuery(sql);
	}

	public List<Map<String, Object>> getAverageAgeByRole() {
		String sql = "SELECT ur.RoleDescription AS Role, AVG(u.Age) AS Avg_Age "
				   + "FROM `User` u LEFT JOIN UserRoles ur ON u.UserRoleID = ur.UserRoleID "
				   + "GROUP BY ur.RoleDescription";
		return QueryExecutionModule.executeQuery(sql);
	}

	public List<Map<String, Object>> getMostPopularRoutes(int limit) {
		String sql = "SELECT r.RouteName, COUNT(t.TicketID) AS Tickets_Sold "
			       + "FROM Ticket t JOIN Trip tr ON t.TripID = tr.TripID "
                   + "    JOIN Bus_Info b ON b.BusID = tr.BusID "
                   + "    JOIN Route r ON b.RouteID = r.RouteID "
			       + "GROUP BY r.RouteName ORDER BY Tickets_Sold DESC LIMIT ?";
		return QueryExecutionModule.executeQuery(sql, limit);
	}

	public List<Map<String, Object>> getTotalTicketsSoldPerDay() {
		String sql = "SELECT DATE(TicketDateTime) AS TicketDate, COUNT(*) AS TicketsSold "
				   + "FROM Ticket GROUP BY DATE(TicketDateTime) ORDER BY TicketDate DESC";
		return QueryExecutionModule.executeQuery(sql);
	}

	public List<Map<String, Object>> getTotalRevenuePerDay() {
		String sql = "SELECT DATE(PaymentDate) AS PaymentDay, SUM(Amount) AS Total_Revenue "
				+ "FROM Payment GROUP BY DATE(PaymentDate) ORDER BY PaymentDay DESC";
		return QueryExecutionModule.executeQuery(sql);
	}

	public List<Map<String, Object>> getTopSpenders(int limit) {
		String sql = "SELECT u.Username, SUM(p.Amount) AS Total_Spent "
				   + "FROM Payment p JOIN `User` u ON p.UserID = u.UserID "
				   + "GROUP BY u.Username ORDER BY Total_Spent DESC LIMIT ?";
		return QueryExecutionModule.executeQuery(sql, limit);
	}

	public List<Map<String, Object>> getTotalTripsPerDriver() {
		String sql = "SELECT d.DriverName, COUNT(t.TripID) AS TotalTrips "
				   + "FROM Trip t JOIN Bus_info b ON t.BusID = b.BusID JOIN Driver d ON b.DriverID = d.DriverID "
				   + "GROUP BY d.DriverName ORDER BY TotalTrips DESC";
		return QueryExecutionModule.executeQuery(sql);
	}

	public List<Map<String, Object>> getBusUsageRate() {
		String sql = "SELECT b.PlateNumber, COUNT(t.TripID) AS TripCount "
				   + "FROM Bus_info b LEFT JOIN Trip t ON b.BusID = t.BusID "
				   + "GROUP BY b.PlateNumber ORDER BY TripCount DESC";
		return QueryExecutionModule.executeQuery(sql);
	}

	public List<Map<String, Object>> getAverageRevenuePerRoute() {
		String sql = "SELECT r.RouteName, AVG(p.Amount) AS AvgFare\r\n" +
                     "FROM Payment p\r\n" +
                     "    JOIN Ticket t ON p.PaymentID = t.PaymentID\r\n" +
                     "    JOIN Trip tr ON t.TripID = tr.TripID\r\n" +
                     "    JOIN Bus_Info b ON tr.BusID = b.BusID\r\n" +
                     "    JOIN Route r ON b.RouteID = r.RouteID\r\n" +
                     "GROUP BY r.RouteName;";
		return QueryExecutionModule.executeQuery(sql);
	}
}
