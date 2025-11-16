//Module này để lấy được kết nối đến database.

package com.buspass.db; // (Đây là tên package, mình sẽ tự thay đổi)

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConnection {
    // Thay đổi các thông tin này cho phù hợp với project nhóm mình
    private static final String DB_URL = "";//ví dụ:jdbc:mysql://localhost:3306/bus_pass_system
    private static final String USER = "root";
    private static final String PASS = " ";// Mật khẩu

    // lấy ket nối
    public static Connection getConnection() {
        try {
            // 1.Nạp driver (tùy chon với JDBC 4.0+)
            //Class.forName("com.mysql.cj.jdbc.Driver");

            // 2.Tao kết noi
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            // Xử lu loi kết nối
            System.err.println("Lỗi kết nối database: " + e.getMessage());
            e.printStackTrace();
            return null; // Trả về null nếu khong ket nối được
        }
    }
}