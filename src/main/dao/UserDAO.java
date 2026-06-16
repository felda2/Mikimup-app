package main.dao;

import database.DatabaseConnection;
import main.model.User;
import java.sql.*;

public class UserDAO {

    // Method untuk Login
    public User login(String username, String password) {
        // Query untuk mengambil data user beserta rolenya
        String sql = "SELECT username, role FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role")); // Mengambil 'admin_kasir' atau 'owner'
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Error saat login: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Mengembalikan null jika login gagal
    }

    // Method untuk Register
    public boolean register(User user) {
        String sql = "INSERT INTO users (username, password, nama_lengkap, role) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNamaLengkap());
            ps.setString(4, user.getRole());
            
            return ps.executeUpdate() > 0; // Mengembalikan true jika berhasil
        } catch (SQLException e) {
            System.err.println("Error saat registrasi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}