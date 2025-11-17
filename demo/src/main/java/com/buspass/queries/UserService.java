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
        return rowsAffected > 0; // thành công--> True
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
        return null; // Không tìm thấy
    }

    /**
     * Change the name of the user
     * @param userId the UserID of User table
     * @param username the new name of the user
     * @return true if the user is found and their name was changed successfully
     */
    public boolean updateUsername(Integer userId, String username) {
        String sql = "UPDATE user SET Username = ? " +
                "WHERE UserID = " + userId.toString();
        
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, username);
        return rowsAffected > 0;
    }

    /**
     * Change the password of the user
     * @param userId the UserID of User table
     * @param newPassword the new password of the user
     * @return true if the user is found and their password was changed successfully
     */
    public boolean updatePassword(Integer userId, String newPassword) {
        String sql = "UPDATE user SET UserPassword = ? " +
                "WHERE UserID = " + userId.toString();
        
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, newPassword);
        return rowsAffected > 0;
    }

    /**
     * Change the address of the user
     * @param userId the UserID of User table
     * @param address the new address of the user
     * @return true if the user is found and their address was changed successfully
     */
    public boolean updateAddress(Integer userId, String address) {
        String sql = "UPDATE user SET UserAddreess = ? " +
                "WHERE UserID = " + userId.toString();
        
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, address);
        return rowsAffected > 0;
    }

    public boolean updateAge(Integer userId, int address) {
        String sql = "UPDATE user SET Age = ? " +
                "WHERE UserID = " + userId.toString();
        
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, address);
        return rowsAffected > 0;
    }

    /**
     * Change the UserRoleID of the user
     * @param userId the UserID of User table
     * @param address the new UserRoleID of the user corresponding to the new Role (1: Passenger, 2: Admin)
     * @return true if the user is found and their UserRoleID was changed successfully
     */
    public boolean updateRoleID(Integer userId, int userRoleId) {
        String sql = "UPDATE user SET UserRoleID = ? " +
                "WHERE UserID = " + userId.toString();
        
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, userRoleId);
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
    public boolean deleteUser(Integer userId) {
        String sql = "DELETE FROM user WHERE UserID = " + userId.toString();
        
        int rowsAffected = QueryExecutionModule.executeUpdate(sql);
        return rowsAffected > 0;
    }
}