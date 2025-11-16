//Module này để lấy được kết nối đến database.

package com.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class databaseConnection {
    // Thay đổi các thông tin này cho phù hợp với project nhóm mình

    // Create your own db_credentials.json file similar to sample_credentials.json to store your MySQL credentials
    // so that your password will not be expo

    private static String DB_URL = "";//ví dụ:jdbc:mysql://localhost:3306/bus_pass_system
    private static String USER = "root";
    private static String PASS = "";// Mật khẩu

    // lấy ket nối
    public static Connection getConnection() {
        try {
            // 1.Nạp driver (tùy chon với JDBC 4.0+)
            // Class.forName("com.mysql.cj.jdbc.Driver");

            // Read credentials file from project root (no "..")
            File credFile = new File("db_credentials.txt");
            if (credFile.exists()) {
                try (Scanner scanner = new Scanner(credFile)) {
                    if (scanner.hasNextLine()) DB_URL = scanner.nextLine().trim();
                    if (scanner.hasNextLine()) PASS = scanner.nextLine().trim();
                } catch (Exception ex) {
                    System.err.println("Could not read credentials file: " + ex.getMessage());
                }
            } else {
                System.out.println("Credentials file not found at: " + credFile.getAbsolutePath() + ". Using defaults/constants.");
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