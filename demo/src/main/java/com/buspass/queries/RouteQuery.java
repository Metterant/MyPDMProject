package com.buspass.queries;

import java.util.List;
import java.util.Map;

import com.buspass.db.QueryExecutionModule;

public class RouteQuery {

	public boolean registerRoute(String routeName, String startLocation, String endLocation, double fare, float distance, String times) {
		String sql = "INSERT INTO Route (RouteName, StartLocation, EndLocation, FARE, Distance, Times) VALUES (?, ?, ?, ?, ?, ?);";
		int rowsAffected = QueryExecutionModule.executeUpdate(sql, routeName, startLocation, endLocation, fare, distance, times);
		return rowsAffected > 0;
	}

	public Map<String, Object> getRouteById(int routeId) {
		String sql = "SELECT * FROM Route WHERE RouteID = ?";
		List<Map<String, Object>> routes = QueryExecutionModule.executeQuery(sql, routeId);
		if (!routes.isEmpty()) return routes.get(0);
		return null;
	}

	public List<Map<String, Object>> getAllRoutes() {
		String sql = "SELECT * FROM Route";
		return QueryExecutionModule.executeQuery(sql);
	}

	/* ADMIN PRIVILEGES */

	public boolean updateRouteName(int routeId, String routeName) {
		String sql = "UPDATE Route SET RouteName = ? WHERE RouteID = ?";
		int rowsAffected = QueryExecutionModule.executeUpdate(sql, routeName, routeId);
		return rowsAffected > 0;
	}

	public boolean updateStartLocation(int routeId, String startLocation) {
		String sql = "UPDATE Route SET StartLocation = ? WHERE RouteID = ?";
		int rowsAffected = QueryExecutionModule.executeUpdate(sql, startLocation, routeId);
		return rowsAffected > 0;
	}

	public boolean updateEndLocation(int routeId, String endLocation) {
		String sql = "UPDATE Route SET EndLocation = ? WHERE RouteID = ?";
		int rowsAffected = QueryExecutionModule.executeUpdate(sql, endLocation, routeId);
		return rowsAffected > 0;
	}

	public boolean updateFare(int routeId, double fare) {
		String sql = "UPDATE Route SET FARE = ? WHERE RouteID = ?";
		int rowsAffected = QueryExecutionModule.executeUpdate(sql, fare, routeId);
		return rowsAffected > 0;
	}

	public boolean updateDistance(int routeId, float distance) {
		String sql = "UPDATE Route SET Distance = ? WHERE RouteID = ?";
		int rowsAffected = QueryExecutionModule.executeUpdate(sql, distance, routeId);
		return rowsAffected > 0;
	}

	public boolean removeRoute(int routeId) {
		String sql = "DELETE FROM Route WHERE RouteID = ?";
		int rowsAffected = QueryExecutionModule.executeUpdate(sql, routeId);
		return rowsAffected > 0;
	}

}
