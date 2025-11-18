//Module này để lấy được kết nối đến database.

package com.buspass.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class databaseConnection {
    // Thay đổi các thông tin này cho phù hợp với project nhóm mình

    // Create your own db_credentials.json file similar to sample_credentials.json to store your MySQL credentials
    // so that your password will not be expo

    private static String DB_URL = "jdbc:mysql://localhost:3306/?user=root";//ví dụ:jdbc:mysql://localhost:3306/bus_pass_system
    private static String USER = "root";
    private static String PASS = "";// Mật khẩu

    // lấy ket nối
    public static Connection getConnection() {
        try {
            // 1.Nạp driver (tùy chon với JDBC 4.0+)
            // Class.forName("com.mysql.cj.jdbc.Driver");

            Dotenv dotenv = Dotenv.configure()
                .filename("db_credentials.env")
                .load();

            // Access the variables
            String DB_URL = dotenv.get("DB_URL");
            String PASS = dotenv.get("DB_PASSWORD");

            // System.out.println("Connection Successful!");

            if (DB_URL == null || DB_URL.isBlank()) {
                System.err.println("Database URL is empty. Ensure that db_credentials.env file containing DB_URL exists in the project root or DB_URL constant is set.");
                return null;
            }

            // 2.Tao kết noi
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            // Xử lu loi kết nối
            System.err.println("Database Connection Error: " + e.getMessage());
            e.printStackTrace();
            return null; // Trả về null nếu khong ket nối được
        }
    }
}