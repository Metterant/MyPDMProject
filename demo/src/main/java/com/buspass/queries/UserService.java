//Khi muốn thêm một người dùng mới

package com.buspass.queries;

import java.util.List;
import java.util.Map;

import com.buspass.db.QueryExecutionModule;

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
    public boolean registerUser(String username, String password, int age, String phone, String address, int userRoleID) {
        String sql = "INSERT INTO user(Username, UserPassword, Age, Phone, UserAddress, UserRoleID) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        // Gọi phương thức executeUpdate từ module của nhóm
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, username, password , age, phone, address, userRoleID);
        return rowsAffected > 0;
    }

    //2: Lấy thông tin người dùng bằng ID (Dùng SELECT)
    public Map<String, Object> getUserById(int userId) {
        String sql = "SELECT UserID, Username, Age, Phone, UserAddress, RoleDescription " + //
            "FROM User JOIN UserRoles ON User.UserRoleID = UserRoles.UserRoleID WHERE UserID = ?";
        // Gọi phương thức executeQuery từ module của nhóm
        List<Map<String, Object>> user = QueryExecutionModule.executeQuery(sql, userId);
        if (!user.isEmpty()) {
            return user.get(0); // Trả về người dùng đầu tiên tìm thấy
        }
        return null; // Not found
    }

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
    public boolean updateUsername(int userId, String username) {
        String sql = "UPDATE User SET Username = ? WHERE UserID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, username, userId);
        return rowsAffected > 0;
    }

    /**
     * Change the password of the user
     * @param userId the UserID of User table
     * @param newPassword the new password of the user
     * @return true if the user is found and their password was changed successfully
     */
    public boolean updatePassword(int userId, String newPassword) {
        String sql = "UPDATE User SET UserPassword = ? WHERE UserID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, newPassword, userId);
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
     * TODO: Make sure to implement confirmation to aovid accidential terminiation
     * 
     * @param userId the UserID of the User table
     * @return whether the User is found and their profile was deleted successfully
     */
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM User WHERE UserID = ?";
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, userId);
        return rowsAffected > 0;
    }
}