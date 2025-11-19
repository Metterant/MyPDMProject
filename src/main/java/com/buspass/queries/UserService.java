package com.buspass.queries;

import java.util.List;
import java.util.Map;

import com.buspass.db.QueryExecutionModule;
import com.buspass.utils.AuthUtils;
import com.buspass.utils.StringUtils;

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
        String sql = "INSERT INTO user(Username, UserPassword, FullName, Age, Phone, UserAddress, UserRoleID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        String hashPW = AuthUtils.hashPassword(plainPassword);
        fullName = StringUtils.normalizeStr(fullName);


        int rowsAffected = QueryExecutionModule.executeUpdate(sql, username, hashPW , fullName, age, phone, address, userRoleID);
        return rowsAffected > 0;
    }


    public Map<String, Object> getUserById(int userId) {
        String sql = "SELECT UserID, Username, FullName, Age, Phone, UserAddress, RoleDescription " + //
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
     * Change the Username of the user
     * @param userId the UserID of User table
     * @param username the new name of the user
     * @return 1 if the user is found and their name was changed successfully, or -1 if the new Username is not valid,
     * or 0 if the Username already exists
     */
    public int updateUsername(int userId, String username) {
        if (!AuthUtils.isValidUsername(username))
            return -1;

        String sql = "UPDATE User SET Username = ? WHERE UserID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, username, userId);
        return (rowsAffected > 0) ? 1 : 0;
    }

    /**
     * Change the password of the user
     * @param userId the UserID of User table
     * @param plainPassword the new password of the user
     * @return true if the user is found and their password was changed successfully
     */
    public boolean updatePassword(int userId, String plainPassword) {
        String hashPW = AuthUtils.hashPassword(plainPassword);

        String sql = "UPDATE User SET UserPassword = ? WHERE UserID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, hashPW, userId);
        return rowsAffected > 0;
    }

    /**
     * Change the Full Name of the User
     * @param userId the UserID of the User
     * @param fullName the Full Name the User wants to change to 
     * @return true if the update was executed successfully
     */
    public boolean updateFullName(int userId, String fullName) {
        fullName = StringUtils.normalizeStr(fullName);

        String sql = "UPDATE User SET FullName = ? WHERE UserID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, fullName, userId);
        return (rowsAffected > 0);
    }

    public boolean updateAge(int userId, int age) {
        String sql = "UPDATE User SET Age = ? WHERE UserID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, age, userId);
        return rowsAffected > 0;
    }
    
    
    public boolean updatePhoneNumber(int userId, String phoneNumber) {
        String sql = "UPDATE User SET Phone = ? WHERE UserID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, phoneNumber, userId);
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