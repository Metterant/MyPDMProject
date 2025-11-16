//Khi muốn thêm một người dùng mới

package com.buspass.db;

import java.util.List;
import java.util.Map;

public class UserService {
    public enum UserRoles {
        Passenger("Passenger"),
        Admin("Admin");

        private final String text;

        UserRoles(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    //1: Đăng ký người dùng mới (Dùng INSERT)
    public boolean registerUser(String username, Integer age, String phone, String address ,UserRoles userRole) {
        String sql = "INSERT INTO user(Username, Age, Phone, Address, Role) " +
                "VALUES (?, ?, ?, ?, ?)";
        // Gọi phương thức executeUpdate từ module của nhóm
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, username, age, phone, address, userRole.toString());
        return rowsAffected > 0; // thành công--> True
    }

    //2: Lấy thông tin người dùng bằng ID (Dùng SELECT)
    public Map<String, Object> getUserById(int userId) {
        String sql = "SELECT * FROM user WHERE UserID = ?";
        // Gọi phương thức executeQuery từ module của nhóm
        List<Map<String, Object>> user = QueryExecutionModule.executeQuery(sql, userId);
        if (!user.isEmpty()) {
            return user.get(0); // Trả về người dùng đầu tiên tìm thấy
        }
        return null; // Không tìm thấy
    }
}