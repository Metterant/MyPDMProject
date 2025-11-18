package com.buspass.queries;

import java.util.List;
import java.util.Map;

import com.buspass.db.QueryExecutionModule;

public class DriverQuery {
    
    //#region ADMIN PRIVILEDGES

    public Map<String, Object> getDriverById(int driverId) {
        String sql = "SELECT * FROM Driver WHERE DriverID = ?;";

        List<Map<String, Object>> drivers = QueryExecutionModule.executeQuery(sql, driverId);

        if (!drivers.isEmpty()) {
            return drivers.get(0);
        }
        return null;
    }

    public List<Map<String, Object>> getAllDrivers() {
        String sql = "SELECT * FROM Driver";
        List<Map<String, Object>> drivers = QueryExecutionModule.executeQuery(sql);

        return drivers;
    }
     
    public List<Map<String, Object>> getAllDrivenBuses(int driverId) {
        String sql = "SELECT d.DriverID, Name, busID, plateNumber, r.RouteID, StartLocation, EndLocation, Distance, Times\r\n" + 
                     "FROM Driver d JOIN Bus_Info b ON d.DriverID = b.DriverID\r\n" +
		             "JOIN Route r on b.RouteID = r.RouteID\r\n" +
                     "WHERE d.DriverID = ?;";
                     
        List<Map<String, Object>> buses = QueryExecutionModule.executeQuery(sql, driverId);

        return buses;
    }

    public boolean registerDriver(String name, int age, String license, String phoneNumber) {    
        String sql = "INSERT INTO Driver (Name, Age, License, Phone)\r\n" +
                     "VALUES(?, ?, ?, ?);";

        int rowsAffected = QueryExecutionModule.executeUpdate(sql, name, age, license, phoneNumber);
        return rowsAffected > 0;
    }

    public boolean updateName(int driverId, String name) {
        String sql = "UPDATE Driver SET Name = ? WHERE DriverID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, name, driverId);
        return rowsAffected > 0;
    }

    public boolean updateAge(int driverId, int age) {
        String sql = "UPDATE Driver SET Age = ? WHERE DriverID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, age, driverId);
        return rowsAffected > 0;
    }

    public boolean updatePhoneNumber(int driverId, String phoneNumber) {
        String sql = "UPDATE Driver SET Phone = ? WHERE DriverID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, phoneNumber, driverId);
        return rowsAffected > 0;
    }

    public boolean deleteDriverById(int driverId) {
        String sql = "DELETE FROM Driver WHERE DriverID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, driverId);
        return rowsAffected > 0;
    }

    //#endregion
}
