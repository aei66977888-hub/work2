package com.example.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("資料庫連線成功！");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT DATABASE()");
                if (rs.next()) {
                    System.out.println("當前資料庫：" + rs.getString(1));
                }
            } else {
                System.out.println("連線失敗：Connection 為 null");
            }
        } catch (SQLException e) {
            System.err.println("連線失敗：");
            e.printStackTrace();
        }
    }
}