package com.buspass.queries;

import java.util.List;
import java.util.Map;

import com.buspass.db.QueryExecutionModule;

public class TripQuery {

    public Map<String, Object> getTripById(int tripId) {
        String sql = "SELECT * FROM Trip WHERE TripID = ?";
        List<Map<String, Object>> trips = QueryExecutionModule.executeQuery(sql, tripId);
        if (!trips.isEmpty()) return trips.get(0);
        return null;
    }

    public List<Map<String, Object>> getAllTrips() {
        String sql = "SELECT * FROM Trip";
        return QueryExecutionModule.executeQuery(sql);
    }

    /** Trips joined with Buses and Routes */
    public List<Map<String, Object>> getTripsWithJoin() {
        String sql = "SELECT t.TripID, t.Date, t.DepartureTime, t.ArrivalTime, b.PlateNumber, r.RouteName "
                + "FROM Trip t JOIN Bus_info b ON t.BusID = b.BusID JOIN Route r ON b.RouteID = r.RouteID";
        return QueryExecutionModule.executeQuery(sql);
    }

    /** Trips joined with Buses and Routes */
    public List<Map<String, Object>> getTripsWithJoinAndDrivers() {
        String sql = 
                "SELECT t.TripID, t.Date, t.DepartureTime, t.ArrivalTime, b.PlateNumber, r.RouteName, d.Name AS DriverName "
                + "FROM Trip t "
                + "JOIN Bus_info b ON t.BusID = b.BusID "
                + "JOIN Route r ON b.RouteID = r.RouteID "
                + "JOIN Driver d ON b.DriverID = d.DriverID";
        return QueryExecutionModule.executeQuery(sql);
    }

    //#region ADMIN PRIVILEDGES

    public boolean createTrip(String date, String departureTime, String arrivalTime, int busId, int routeId) {
        String sql = "INSERT INTO Trip (Date, DepartureTime, ArrivalTime, BusID, RouteID) VALUES (?, ?, ?, ?, ?);";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, date, departureTime, arrivalTime, busId, routeId);
        return rowsAffected > 0;
    }

    public boolean removeTrip(int tripId) {
        String sql = "DELETE FROM Trip WHERE TripID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, tripId);
        return rowsAffected > 0;
    }

    //#endregion
}
