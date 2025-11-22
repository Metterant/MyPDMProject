package com.buspass.queries;

import java.util.List;
import java.util.LinkedHashMap;

import com.buspass.db.QueryExecutionModule;
import com.buspass.utils.MathUtils;

public class RouteQuery {
	public LinkedHashMap<String, Object> getRouteById(int routeId) {
		String sql = "SELECT * FROM Route WHERE RouteID = ?";
		List<LinkedHashMap<String, Object>> routes = QueryExecutionModule.executeQuery(sql, routeId);
		if (!routes.isEmpty()) return routes.get(0);
		return null;
	}

	public List<LinkedHashMap<String, Object>> getAllRoutes() {
		String sql = "SELECT * FROM Route";
		return QueryExecutionModule.executeQuery(sql);
	}

	//#region ADMIN PRIVILEDGES
    
	public boolean registerRoute(String routeName, String startLocation, String endLocation, double fare, float distance, int durationInMinutes) {
		String sql = "INSERT INTO Route (RouteName, StartLocation, EndLocation, Fare, Distance, Duration) VALUES (?, ?, ?, ?, ?, ?);";

		String sqlTimeString = MathUtils.convertToTimeString(durationInMinutes).toString();

		int rowsAffected = QueryExecutionModule.executeUpdate(sql, routeName, startLocation, endLocation, fare, distance, sqlTimeString);
		return rowsAffected > 0;
	}
	
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
		String sql = "UPDATE Route SET Fare = ? WHERE RouteID = ?";
		int rowsAffected = QueryExecutionModule.executeUpdate(sql, fare, routeId);
		return rowsAffected > 0;
	}

	public boolean updateDistance(int routeId, float distance) {
		String sql = "UPDATE Route SET Distance = ? WHERE RouteID = ?";
		int rowsAffected = QueryExecutionModule.executeUpdate(sql, distance, routeId);
		return rowsAffected > 0;
	}

	public boolean updateDuration(int routeId, int durationInMinutes) {
		String sql = "UPDATE Route SET Duration = ? WHERE RouteID = ?";

		String sqlTimeString = MathUtils.convertToTimeString(durationInMinutes).toString();

		int rowsAffected = QueryExecutionModule.executeUpdate(sql, sqlTimeString, routeId);
		return rowsAffected > 0;
	}

	public boolean removeRoute(int routeId) {
		String sql = "DELETE FROM Route WHERE RouteID = ?";
		int rowsAffected = QueryExecutionModule.executeUpdate(sql, routeId);
		return rowsAffected > 0;
	}

    //#endregion
}
