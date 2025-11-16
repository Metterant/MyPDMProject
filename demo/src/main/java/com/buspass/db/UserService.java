//Khi muốn thêm một người dùng mới

package com.buspass.db;

import com.buspass.db.QueryExecutionModule; // Import module

import java.util.List;
import java.util.Map;

public class UserService {
    //1: Đăng ký người dùng mới (Dùng INSERT)
    public boolean registerUser(String username, String password, String email, String phone) {
        String sql = "INSERT INTO User (username, password_hash, email, phone_number, created_at) " +
                "VALUES (?, ?, ?, ?, NOW())";
        // Gọi phương thức executeUpdate từ module của nhóm
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, username, password, email, phone);
        return rowsAffected > 0; // thành công--> True
    }

    //2: Lấy thông tin người dùng bằng ID (Dùng SELECT)
    public Map<String, Object> getUserById(int userId) {
        String sql = "SELECT user_id, username, email, phone_number FROM User WHERE user_id = ?";
        // Gọi phương thức executeQuery từ module của nhóm
        List<Map<String, Object>> users = QueryExecutionModule.executeQuery(sql, userId);
        if (!users.isEmpty()) {
            return users.get(0); // Trả về người dùng đầu tiên tìm thấy
        }
        return null; // Không tìm thấy
    }

    //3: Gia hạn thẻ (Dùng UPDATE)
    public boolean renewPass(int passId, java.util.Date newExpiryDate) {
        String sql = "UPDATE Pass SET expiry_date = ?, status = 'ACTIVE' WHERE pass_id = ?";
        // Chuyển java.util.Date sang java.sql.Date
        java.sql.Date sqlExpiryDate = new java.sql.Date(newExpiryDate.getTime());
        int rowsAffected = QueryExecutionModule.executeUpdate(sql, sqlExpiryDate, passId);
        return rowsAffected > 0;
    }
}