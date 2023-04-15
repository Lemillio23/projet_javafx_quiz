package com.example.authentification;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionUtil {
    private static Connection conn = null;
    private static final String URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Connection failed " + ex.getMessage());
        }
        return conn;
    }

    public static void closeConnection(PreparedStatement stmt) {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException ex) {
            System.out.println("Error while closing connection " + ex.getMessage());
        }
    }

    public static void close(PreparedStatement stmt) {
    }
}