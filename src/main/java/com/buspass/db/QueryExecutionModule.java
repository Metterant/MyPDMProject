package com.buspass.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;

public class QueryExecutionModule {
    //Phương thức dùng chung cho INSERT, UPDATE, DELETE.
    public static int executeUpdate(String sql, Object... params) {
        // Sử dụng try-with-resources để đảm bảo kết nối và statement luôn được đóng
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 1. Prepare PreparedStatement
            setParameters(pstmt, params);
            // 2. Execute the statement
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errors occured in executeUpdate: " + e.getMessage());
            e.printStackTrace();
            return -1; // Error
        }
    }

    public static List<LinkedHashMap<String, Object>> executeQuery(String sql, Object... params) {
        List<LinkedHashMap<String, Object>> results = new ArrayList<>();

        // try-with-resources sẽ tự động đóng Connection, PreparedStatement, và ResultSet
        try (Connection conn = databaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 1. Thiết lập các tham số
            setParameters(pstmt, params);
            // 2. Thực thi truy vấn và lấy ResultSet
            try (ResultSet rs = pstmt.executeQuery()) {
                // 3. Lấy thông tin metadata (tên cột)
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                // 4. Duyệt qua từng dòng kết quả
                while (rs.next()) {
                    LinkedHashMap<String, Object> row = new LinkedHashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnLabel(i); // Lấy tên cột
                        Object value = rs.getObject(i); // Lấy giá trị
                        row.put(columnName, value);
                    }
                    results.add(row);
                }
            }

        } catch (SQLException e) {
            System.err.println("Errors occured in executeQuery: " + e.getMessage());
            e.printStackTrace();
        }
        // Return an empty list when encounterd an error
        return results;
    }
    //PreparedStatement parameters Helper.
    private static void setParameters(PreparedStatement pstmt, Object... params) throws SQLException {
        if (params == null) {
            return;
        }
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
    }
}