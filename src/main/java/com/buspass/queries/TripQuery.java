package com.buspass.queries;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import com.buspass.db.QueryExecutionModule;

public class TripQuery {

    public List<Map<String, Object>> getUpcomingTrips() {
        String sql = "SELECT TripID, TripDate, DepartureTime, ArrivalTime, Capacity, \r\n" +
                        "    RouteName, StartLocation, EndLocation, Duration\r\n" +
                        "FROM Trip tr JOIN Bus_Info b ON tr.BusID = b.BusID\r\n" +
                        "    JOIN Route r ON b.RouteID = r.RouteID\r\n" +
                        "WHERE (TripDate > CURDATE()\r\n" +
                        "    OR (TripDate = CURDATE()\r\n" +
                        "       AND DepartureTime > NOW()))\r\n" +
                        "ORDER BY TripDate, DepartureTime";
        return QueryExecutionModule.executeQuery(sql);
    }

    public List<Map<String, Object>> getFilteredTrips(String date, String routes) {
        // ensure routes are quoted for SQL IN clause
        String quotedRoutes = wrapRouteNamesForSql(routes);

        String sql = "SELECT TripID, TripDate, DepartureTime, ArrivalTime, Capacity, \r\n" +
                        "    RouteName, StartLocation, EndLocation, Duration\r\n" +
                        "FROM Trip tr JOIN Bus_Info b ON tr.BusID = b.BusID\r\n" +
                        "    JOIN Route r ON b.RouteID = r.RouteID\r\n" +
                        "WHERE (TripDate > ?\r\n" +
                        "    OR (TripDate = ?\r\n" +
                        "       AND DepartureTime = NOW()))\r\n" +
                        "    AND RouteName IN (" + quotedRoutes + ")\r\n" +
                        "ORDER BY TripDate, DepartureTime";

        return QueryExecutionModule.executeQuery(sql, date, date);
    }

    public List<Map<String, Object>> getFilteredRoutesTrips(String routes) {
        String quotedRoutes = wrapRouteNamesForSql(routes);

        String sql = "SELECT TripID, TripDate, DepartureTime, ArrivalTime, Capacity, \r\n" +
                        "    RouteName, StartLocation, EndLocation, Duration\r\n" +
                        "FROM Trip tr JOIN Bus_Info b ON tr.BusID = b.BusID\r\n" +
                        "    JOIN Route r ON b.RouteID = r.RouteID\r\n" +
                        "WHERE (TripDate > CURDATE()\r\n" +
                        "    OR (TripDate = CURDATE()\r\n" +
                        "       AND DepartureTime > NOW()))\r\n" +
                        "    AND RouteName IN (" + quotedRoutes + ")\r\n" +
                        "ORDER BY TripDate, DepartureTime";
        System.out.println(sql);

        return QueryExecutionModule.executeQuery(sql);
    }

    /**
     * Wrap each comma-separated token in the provided routeNames string with single quotes
     * so it can be safely embedded into an SQL IN clause.
     * Example: "8, 10-12" -> "'8','10-12'"
     */
    public static String wrapRouteNamesForSql(String routeNames) {
        if (routeNames == null) return "''";
        
        String trimmed = routeNames.trim();
        if (trimmed.isEmpty()) return "''";

        String[] parts = trimmed.split("\\s*,\\s*");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            String p = parts[i].trim();
            if (p.isEmpty()) continue;
            // escape any single quotes just in case (though tokens are digits/hyphen)
            p = p.replace("'", "''");
            if (sb.length() > 0) sb.append(',');
            sb.append('\'').append(p).append('\'');
        }

        if (sb.length() == 0) return "''";
        return sb.toString();
    }

    public List<Map<String, Object>> getFilteredDateTrips(String date) {
        String sql = "SELECT TripID, TripDate, DepartureTime, ArrivalTime, Capacity, \r\n" + //
                        "    RouteName, StartLocation, EndLocation, Duration\r\n" + //
                        "FROM Trip tr JOIN Bus_Info b ON tr.BusID = b.BusID\r\n" + //
                        "    JOIN Route r ON b.RouteID = r.RouteID\r\n" + //
                        "WHERE TripDate > ?\r\n" + //
                        "    OR (TripDate = CURDATE()\r\n" + //
                        "       AND DepartureTime >= NOW())\r\n" +
                        "ORDER BY TripDate, DepartureTime";

        return QueryExecutionModule.executeQuery(sql, date);
    }

    //#region ADMIN PRIVILEDGES
    
    public Map<String, Object> getTripById(int tripId) {
        String sql = "SELECT * FROM Trip WHERE TripID = ? ";
        List<Map<String, Object>> trips = QueryExecutionModule.executeQuery(sql, tripId);
        if (!trips.isEmpty()) return trips.get(0);
        return null;
    }

    public List<Map<String, Object>> getAllTrips() {
        String sql = "SELECT * FROM Trip\r\n" +
                     "ORDER BY TripDate, DepartureTime";

        return QueryExecutionModule.executeQuery(sql);
    }

    /** Trips joined with Buses and Routes */
    public List<Map<String, Object>> getTripsWithJoin() {
        String sql = "SELECT t.TripID, t.TripDate, t.DepartureTime, t.ArrivalTime, b.PlateNumber, r.RouteName "
                   + "FROM Trip t "
                   + "    LEFT JOIN Bus_info b ON t.BusID = b.BusID "
                   + "    LEFT JOIN Route r ON b.RouteID = r.RouteID "
                   + "ORDER BY TripDate, DepartureTime";
        return QueryExecutionModule.executeQuery(sql);
    }

    /** Trips joined with Buses and Routes */
    public List<Map<String, Object>> getTripsWithJoinAndDrivers() {
        String sql = 
                "SELECT t.TripID, t.TripDate, t.DepartureTime, t.ArrivalTime, b.PlateNumber, r.RouteName, DriverName "
                + "FROM Trip t "
                + "LEFT JOIN Bus_info b ON t.BusID = b.BusID "
                + "LEFT JOIN Route r ON b.RouteID = r.RouteID "
                + "LEFT JOIN Driver d ON b.DriverID = d.DriverID "
                + "ORDER BY TripDate, DepartureTime";
        return QueryExecutionModule.executeQuery(sql);
    }

    public boolean createTrip(String tripDate, String departureTime, String arrivalTime, int busId) {
        // Schema Trip: TripDate, DepartureTime, ArrivalTime, BusID (no RouteID column)
        String sql = "INSERT INTO Trip (TripDate, DepartureTime, ArrivalTime, BusID) VALUES (?, ?, ?, ?);";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, tripDate, departureTime, arrivalTime, busId);
        return rowsAffected > 0;
    }

    public boolean removeTrip(int tripId) {
        String sql = "DELETE FROM Trip WHERE TripID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, tripId);
        return rowsAffected > 0;
    }

    /* Update operations */
    public boolean updateTripDate(int tripId, String newDate) {
        String sql = "UPDATE Trip SET TripDate = ? WHERE TripID = ?";
        return QueryExecutionModule.executeUpdate(sql, newDate, tripId) > 0;
    }

    public boolean updateTimes(int tripId, String departureTime, String arrivalTime) {
        String sql = "UPDATE Trip SET DepartureTime = ?, ArrivalTime = ? WHERE TripID = ?";
        return QueryExecutionModule.executeUpdate(sql, departureTime, arrivalTime, tripId) > 0;
    }

    public boolean updateBusId(int tripId, int busId) {
        String sql = "UPDATE Trip SET BusID = ? WHERE TripID = ?";
        return QueryExecutionModule.executeUpdate(sql, busId, tripId) > 0;
    }

    /**
     * Generate random Trips between today and today + daysAhead.
     * Departure time random between 06:00 and 20:00, duration 30 - 240 minutes.
     * Picks random BusID from existing buses.
     * @param count number of trips to generate
     * @param daysAhead inclusive upper bound of day offset from today
     * @return number of trips successfully inserted
     */
    public int generateRandomTrips(int count, int daysAhead) {
        List<Map<String,Object>> buses = QueryExecutionModule.executeQuery("SELECT BusID FROM Bus_info");

        if (buses == null || buses.isEmpty()) return 0;

        LocalDate today = LocalDate.now();
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss");

        int inserted = 0;
        for (int i = 0; i < count; i++) {
            int busIndex = rng.nextInt(buses.size());
            int busId = Integer.parseInt(buses.get(busIndex).get("BusID").toString());
            int dayOffset = rng.nextInt(daysAhead + 1); // 0..daysAhead
            LocalDate tripDate = today.plusDays(dayOffset);
            int depMinutes = rng.nextInt(6 * 60, 20 * 60); // 06:00 to <20:00
            int durationMinutes = rng.nextInt(30, 241); // 30..240
            LocalTime departure = LocalTime.of(depMinutes / 60, depMinutes % 60);
            LocalTime arrival = departure.plusMinutes(durationMinutes);

            if (arrival.isBefore(departure)) arrival = departure.plusHours(1); // safety
            String dateStr = tripDate.toString();
            String depStr = departure.format(timeFmt);
            String arrStr = arrival.format(timeFmt);

            int rows = QueryExecutionModule.executeUpdate(
                "INSERT INTO Trip (TripDate, DepartureTime, ArrivalTime, BusID) VALUES (?, ?, ?, ?)",
                dateStr, depStr, arrStr, busId
            );
            if (rows > 0) inserted += 1;
        }
        return inserted;
    }

    /**
     * Lấy giá vé (Fare) của một chuyến đi cụ thể.
     * @param tripId ID của chuyến đi.
     * @return Giá vé (Double) hoặc null nếu không tìm thấy chuyến đi/tuyến đường.
     */
    public Double getFareByTripId(int tripId) {
        // SQL: Join Trip, Bus_info, và Route để lấy giá Fare
        String sql = "SELECT r.Fare " +
                    "FROM Trip t " +
                    "JOIN Bus_info b ON t.BusID = b.BusID " +
                    "JOIN Route r ON b.RouteID = r.RouteID " +
                    "WHERE t.TripID = ?";
        
        // Giả sử QueryExecutionModule.executeQuery trả về List<Map<String, Object>>
        List<Map<String, Object>> result = QueryExecutionModule.executeQuery(sql, tripId);
        
        if (result != null && !result.isEmpty()) {
            Object fareObj = result.get(0).get("Fare");
            // Chuyển đổi giá trị DECIMAL từ SQL sang Double trong Java
            if (fareObj instanceof Number) {
                return ((Number) fareObj).doubleValue();
            }
        }
        return null;
    }

    /** Convenience method to generate 50 trips over next 20 days */
    public int generateRandomTripsDefault() { return generateRandomTrips(50, 20); }

    //#endregion
}
