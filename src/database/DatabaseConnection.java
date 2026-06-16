package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/mikimup_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Koneksi database berhasil.");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL tidak ditemukan.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Koneksi database gagal.");
            e.printStackTrace();
        }

        return connection;
    }
}