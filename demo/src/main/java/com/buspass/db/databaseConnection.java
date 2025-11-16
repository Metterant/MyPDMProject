//Module này để lấy được kết nối đến database.

package com.buspass.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

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

            // Prefer loading credentials from classpath (src/main/resources packaged into target/classes)
            boolean loaded = false;
            try (java.io.InputStream in = databaseConnection.class.getResourceAsStream("/db_credentials.txt")) {
                if (in != null) {
                    try (Scanner scanner = new Scanner(in)) {
                        if (scanner.hasNextLine()) DB_URL = scanner.nextLine().trim();
                        if (scanner.hasNextLine()) PASS = scanner.nextLine().trim();
                        loaded = true;
                    }
                }
            } catch (Exception ex) {
                System.err.println("Could not read db_credentials.txt from classpath: " + ex.getMessage());
            }

            // If classpath loading didn't work, try common filesystem locations relative to project root
            if (!loaded) {
                File[] fallbacks = new File[] {
                    new File("demo/src/main/resources/db_credentials.txt"),
                    new File("src/main/resources/db_credentials.txt"),
                    new File("db_credentials.txt")
                };
                for (File credFile : fallbacks) {
                    if (credFile.exists()) {
                        try (Scanner scanner = new Scanner(credFile)) {
                            if (scanner.hasNextLine()) DB_URL = scanner.nextLine().trim();
                            if (scanner.hasNextLine()) PASS = scanner.nextLine().trim();
                            loaded = true;
                            break;
                        } catch (Exception ex) {
                            System.err.println("Could not read credentials file at " + credFile.getAbsolutePath() + ": " + ex.getMessage());
                        }
                    }
                }
                if (!loaded) {
                    System.out.println("Credentials not found in resources or fallback paths; using defaults/constants.");
                }
            }

            System.out.println("Connection Successful!");

            if (DB_URL == null || DB_URL.isBlank()) {
                System.err.println("Database URL is empty. Ensure db_credentials.txt (in src/main/resources) or DB_URL constant is set.");
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