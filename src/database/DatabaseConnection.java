package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Sesuaikan URL jika port MySQL kamu bukan 3306
    private static final String URL = "jdbc:mysql://localhost:3306/mikimup_db";
    private static final String USER = "root";
    private static final String PASS = ""; // Biasanya kosong kalau pakai XAMPP

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}