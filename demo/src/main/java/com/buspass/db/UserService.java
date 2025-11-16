//Khi muốn thêm một người dùng mới

package com.buspass.db;

import java.util.List;
import java.util.Map;

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
    public boolean registerUser(String username, int age, String phone, String address, int userRoleID) {
        String sql = "INSERT INTO user(Username, UserPassword, Age, Phone, UserAddress, UserRoleID) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        // Gọi phương thức executeUpdate từ module của nhóm
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, username, age, phone, address, userRoleID);
        return rowsAffected > 0; // thành công--> True
    }

    //2: Lấy thông tin người dùng bằng ID (Dùng SELECT)
    public Map<String, Object> getUserById(int userId) {
        String sql = "SELECT Username, Age, Phone, UserAddress, RoleDescription " + //
            "FROM User JOIN UserRoles ON User.UserRoleID = UserRoles.UserRoleID WHERE UserID = ?";
        // Gọi phương thức executeQuery từ module của nhóm
        List<Map<String, Object>> user = QueryExecutionModule.executeQuery(sql, userId);
        if (!user.isEmpty()) {
            return user.get(0); // Trả về người dùng đầu tiên tìm thấy
        }
        return null; // Không tìm thấy
    }
}