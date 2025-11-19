package com.buspass.queries;

import java.util.List;
import java.util.Map;

import com.buspass.db.QueryExecutionModule;
import com.buspass.utils.LoginUtils;

import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    //1: Đăng ký người dùng mới (Dùng INSERT)
    /**
     * A method used to create a new user
     * @param username
     * @param age
     * @param phone
     * @param address
     * @param userRoleID: Use dropdown menu (1 -> Passenger, 2 -> Admin)
     * @return
     */
    public boolean registerUser(String username, String plainPassword, String fullName, int age, String phone, String address, int userRoleID) {
        String sql = "INSERT INTO user(Username, UserPassword, Age, Phone, UserAddress, UserRoleID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        String hashPW = LoginUtils.hashPassword(plainPassword);

        int rowsAffected = QueryExecutionModule.executeUpdate(sql, username, hashPW , fullName, age, phone, address, userRoleID);
        return rowsAffected > 0;
    }


    public Map<String, Object> getUserById(int userId) {
        String sql = "SELECT UserID, Username, Age, Phone, UserAddress, RoleDescription " + //
            "FROM User JOIN UserRoles ON User.UserRoleID = UserRoles.UserRoleID WHERE UserID = ?";

        List<Map<String, Object>> users = QueryExecutionModule.executeQuery(sql, userId);
        if (!users.isEmpty()) {
            return users.get(0); // Return the first User
        }
        return null; // Not found
    }

    /** Returns basic details related to Tickets used for Trips */
    public List<Map<String, Object>> getTicketsByUserId(int userId) {
        String sql = "SELECT TicketID, t.TicketDateTime, b.PlateNumber, RouteName, t.TripID, b.BusID, r.RouteID, t.PaymentID\r\n" + //
                     "FROM User u JOIN Ticket t ON u.UserID = t.UserID\r\n" +
                     "    LEFT JOIN Trip tr ON t.TripID = tr.TripID\r\n"         +
                     "    LEFT JOIN Bus_Info b ON b.BusID = tr.BusID\r\n"        +
                     "    LEFT JOIN Route r ON r.RouteID = b.RouteID\r\n"       +
                     "WHERE u.UserID = ?";
            
        List<Map<String, Object>> tickets = QueryExecutionModule.executeQuery(sql, userId);
        
        return tickets;
    }

    /** Returns the details about the trips a specific User has bought Tickets for */
    public List<Map<String, Object>> getTripsTravelByUserId(int userId) {
        String sql = "SELECT tr.Date, b.PlateNumber, RouteName, DepartureTime, " +
                        "ArrivalTime, TicketID, t.TripID, b.BusID, r.RouteID\r\n" + //
                     "FROM User u JOIN Ticket t ON u.UserID = t.UserID\r\n" +
                     "    LEFT JOIN Trip tr ON t.TripID = tr.TripID\r\n"         +
                     "    LEFT JOIN Bus_Info b ON b.BusID = tr.BusID\r\n"        +
                     "    LEFT JOIN Route r ON r.RouteID = b.RouteID\r\n"       +
                     "WHERE u.UserID = ?";
            
        List<Map<String, Object>> trips = QueryExecutionModule.executeQuery(sql, userId);
        
        return trips;
    }

    //#region ADMIN PRIVILEDGES
    
    public List<Map<String, Object>> getAllUsers() {
        String sql = "SELECT UserID, Username, Age, Phone, UserAddress, RoleDescription " + //
            "FROM User JOIN UserRoles ON User.UserRoleID = UserRoles.UserRoleID";
            
        List<Map<String, Object>> users = QueryExecutionModule.executeQuery(sql);
        
        return users;
    }

    /**
     * Change the name of the user
     * @param userId the UserID of User table
     * @param username the new name of the user
     * @return true if the user is found and their name was changed successfully
     */
    public boolean updateUserName(int userId, String username) {
        String sql = "UPDATE User SET Username = ? WHERE UserID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, username, userId);
        return rowsAffected > 0;
    }

    /**
     * Change the password of the user
     * @param userId the UserID of User table
     * @param plainTextPassword the new password of the user
     * @return true if the user is found and their password was changed successfully
     */
    public boolean updatePassword(int userId, String plainTextPassword) {
        String hashedPassword = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());

        String sql = "UPDATE User SET UserPassword = ? WHERE UserID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, hashedPassword, userId);
        return rowsAffected > 0;
    }

    /**
     * Change the address of the user
     * @param userId the UserID of User table
     * @param address the new address of the user
     * @return true if the user is found and their address was changed successfully
     */
    public boolean updateAddress(int userId, String address) {
        String sql = "UPDATE User SET UserAddress = ? WHERE UserID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, address, userId);
        return rowsAffected > 0;
    }

    public boolean updateAge(int userId, int age) {
        String sql = "UPDATE User SET Age = ? WHERE UserID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, age, userId);
        return rowsAffected > 0;
    }

    /**
     * Change the UserRoleID of the user
     * @param userId the UserID of User table
     * @param address the new UserRoleID of the user corresponding to the new Role (1: Passenger, 2: Admin)
     * @return true if the user is found and their UserRoleID was changed successfully
     */
    public boolean updateRoleID(int userId, int userRoleId) {
        String sql = "UPDATE User SET UserRoleID = ? WHERE UserID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, userRoleId, userId);
        return rowsAffected > 0;
    }

    /**
     * Delete User from the User Table by UserID
     * 
     * The Passenger User can only delete their own Profile and the Admin can delete any user 
     * TODO: Make sure to implement confirmation to aovid accidental terminiation
     * 
     * @param userId the UserID of the User table
     * @return whether the User is found and their profile was deleted successfully
     */
    public boolean deleteUserById(int userId) {
        String sql = "DELETE FROM User WHERE UserID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, userId);
        return rowsAffected > 0;
    }

    //#endregion
}