package com.buspass.queries;

import java.util.List;
import java.util.Map;

import com.buspass.db.QueryExecutionModule;

public class BusQuery {
    public boolean registerBus(String plateNumber, int capacity, int driverId, int routeId) {
        String sql = "INSERT INTO bus_info (PlateNumber, Capacity, DriveID, RouteID) " + 
            "VALUES(?,?,?,?);";

        int rowsAffected = QueryExecutionModule.executeUpdate(sql, plateNumber, capacity , driverId, routeId);
        return rowsAffected > 0;
    }
    
    public Map<String, Object> getBusInfoById(int busId) {
        String sql = "SELECT * FROM bus_info WHERE BusID = ?";

        List<Map<String, Object>> bus = QueryExecutionModule.executeQuery(sql, busId);
        if (!bus.isEmpty()) {
            return bus.get(0); // Trả về người dùng đầu tiên tìm thấy
        }
        return null; // Not found    
    }
    
    public List<Map<String, Object>> getBusJoinRouteAndDriverById() {
        String sql = "SELECT b.BusID, b.PlateNumber, b.Capacity,\r\n" + //
                        "d.Name AS DriverName, r.RouteName\r\n" + //
                        "FROM Bus_info b\r\n" + //
                        "JOIN Driver d ON b.DriveID = d.DriveID\r\n" + //
                        "JOIN Route r ON b.RouteID = r.RouteID;";

        List<Map<String, Object>> busses = QueryExecutionModule.executeQuery(sql);
        
        return busses; // Not found    
    }

    /* ADMIN PRIVILEDGES */

    public boolean updateBusPlate(Integer busId, String plateNumber) {
        String sql = "UPDATE bus_info SET plateNumber=? " +
                "WHERE UserID = " + busId.toString();
        
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, plateNumber);
        return rowsAffected > 0;
    }

    public boolean updateCapacity(Integer busId, int newCapacity) {
        String sql = "UPDATE bus_info SET capacity=? " +
                "WHERE UserID = " + busId.toString();
        
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, newCapacity);
        return rowsAffected > 0;
    }

    public boolean updateDriverID(Integer busId, int driverId) {
        String sql = "UPDATE bus_info SET DriverID=? " +
                "WHERE UserID = " + busId.toString();
        
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, driverId);
        return rowsAffected > 0;
    }

    
    public boolean updateRouteID(Integer busId, int routeId) {
        String sql = "UPDATE bus_info SET RouteID=? " +
                "WHERE UserID = " + busId.toString();
        
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, routeId);
        return rowsAffected > 0;
    }

    public boolean removeBus(Integer busId) {
        String sql = "DELETE FROM bus_info WHERE BusID = " + busId.toString();
        
        int rowsAffected = QueryExecutionModule.executeUpdate(sql);
        return rowsAffected > 0;
    }
}
