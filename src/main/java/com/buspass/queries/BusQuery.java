package com.buspass.queries;

import java.util.List;
import java.util.LinkedHashMap;

import com.buspass.db.QueryExecutionModule;

public class BusQuery {
    
    public LinkedHashMap<String, Object> getBusInfoById(int busId) {
        String sql = "SELECT * FROM bus_info WHERE BusID = ?";
        
        List<LinkedHashMap<String, Object>> bus = QueryExecutionModule.executeQuery(sql, busId);
        if (!bus.isEmpty()) {
            return bus.get(0);
        }
        return null; // Not found    
    }

    
    public List<LinkedHashMap<String, Object>> getAllBuses() {
        String sql = "SELECT b.BusID, b.PlateNumber, b.Capacity, DriverName, r.RouteName\r\n" + //
                    "FROM Bus_info b\r\n" + //
                    "LEFT JOIN Driver d ON b.DriverID = d.DriverID\r\n" + //
                    "LEFT JOIN Route r ON b.RouteID = r.RouteID;";
        
        List<LinkedHashMap<String, Object>> buses = QueryExecutionModule.executeQuery(sql);
        
        return buses; // Not found    
    }
    
    /* ADMIN PRIVILEDGES */
    
    public boolean registerBus(String plateNumber, int capacity, int driverId, int routeId) {
        String sql = "INSERT INTO bus_info (PlateNumber, Capacity, DriverID, RouteID) " + 
            "VALUES(?,?,?,?);";

        int rowsAffected = QueryExecutionModule.executeUpdate(sql, plateNumber, capacity , driverId, routeId);
        return rowsAffected > 0;
    }

    public List<LinkedHashMap<String, Object>> getTripsTraveledById(int busId) {
        String sql = "SELECT TripID, TripDate, r.RouteID, RouteName, Distance, DepartureTime, ArrivalTime, b.DriverID, DriverName\n" + //
                    "FROM Trip t\n" + //
                    "LEFT JOIN Bus_info b ON t.BusID = b.BusID\n" + //
                    "LEFT JOIN Route r ON b.RouteID = r.RouteID\n" + //
                    "LEFT JOIN Driver d ON b.DriverID = d.DriverID\n" + //
                    "WHERE b.BusID = ?;";
        
        List<LinkedHashMap<String, Object>> trips = QueryExecutionModule.executeQuery(sql, busId);
        
        return trips; // Not found    
    }

    public boolean updateBusPlate(int busId, String plateNumber) {
        String sql = "UPDATE Bus_info SET PlateNumber = ? WHERE BusID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, plateNumber, busId);
        return rowsAffected > 0;
    }

    public boolean updateCapacity(int busId, int newCapacity) {
        String sql = "UPDATE Bus_info SET Capacity = ? WHERE BusID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, newCapacity, busId);
        return rowsAffected > 0;
    }

    public boolean updateDriverID(int busId, int driverId) {
        String sql = "UPDATE Bus_info SET DriverID = ? WHERE BusID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, driverId, busId);
        return rowsAffected > 0;
    }

    
    public boolean updateRouteID(int busId, int routeId) {
        String sql = "UPDATE Bus_info SET RouteID = ? WHERE BusID = ? ";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, routeId, busId);
        return rowsAffected > 0;
    }

    public boolean removeBus(int busId) {
        String sql = "DELETE FROM Bus_info WHERE BusID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, busId);
        return rowsAffected > 0;
    }
}
