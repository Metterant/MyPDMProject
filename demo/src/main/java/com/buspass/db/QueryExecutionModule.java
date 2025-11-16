//Module này thực thi truy vấn SQL.
//Mình tạo một lớp với các phương thức tĩnh (static) để dễ dàng gọi từ bất kỳ đâu.
//Lớp này sẽ xử lý PreparedStatement một cách an toàn.

package com.buspass.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryExecutionModule {
    //Phương thức dùng chung cho INSERT, UPDATE, DELETE.
    public static int executeUpdate(String sql, Object... params) {
        // Sử dụng try-with-resources để đảm bảo kết nối và statement luôn được đóng
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 1. Thiết lập các tham số cho PreparedStatement
            setParameters(pstmt, params);
            // 2. Thực thi câu lệnh
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi thực thi executeUpdate: " + e.getMessage());
            e.printStackTrace();
            return -1; // Trả về -1 để báo lỗi
        }
    }
    // Dùng chung cho SELECT.
    //Trả về một List các Map, mỗi Map đại diện cho 1 dòng dữ liệu
    //key là tên cột, value là giá trị
    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> results = new ArrayList<>();

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
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnLabel(i); // Lấy tên cột
                        Object value = rs.getObject(i); // Lấy giá trị
                        row.put(columnName, value);
                    }
                    results.add(row);
                }
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi thực thi executeQuery: " + e.getMessage());
            e.printStackTrace();
            // Trả về list rỗng khi có lỗi
        }
        return results;
    }
    //Trợ giúp (private) để gán tham số vào PreparedStatement.
    private static void setParameters(PreparedStatement pstmt, Object... params) throws SQLException {
        if (params == null) {
            return;
        }
        // Vòng lặp gán tham số, bắt đầu từ index 1
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
    }
}