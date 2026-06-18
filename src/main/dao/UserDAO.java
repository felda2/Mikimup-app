package main.dao;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import main.model.User;

public class UserDAO {

    public User login(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, username.trim());
            ps.setString(2, password.trim());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                users.add(new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean tambahUser(User user) {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean editUser(String usernameLama, User user) {
        String query = "UPDATE users SET username = ?, password = ?, role = ? WHERE username = ?";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setString(4, usernameLama);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hapusUser(String username) {
        String query = "DELETE FROM users WHERE username = ?";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, username);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}