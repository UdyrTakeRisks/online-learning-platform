package com.platform.onlineplatformapp.Persistence;

import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
@Singleton
public class DBConnection {
    private static final String URL = "jdbc:mysql://mariadb:3306/platformDB";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123";

    // Load the SQL Server JDBC driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading SQL Server JDBC driver.");
            e.printStackTrace();
            throw new RuntimeException("Error loading SQL Server JDBC driver.", e);
        }
    }

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error connecting to the database.");
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database.", e);
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection.");
                e.printStackTrace();
            }
        }
    }
}
