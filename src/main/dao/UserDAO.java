package main.dao;

import database.DatabaseConnection;
import main.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDAO {

    public User login(String username, String password) {
        User user = null;

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = mapResultSetToUser(rs);
                }
            }

        } catch (Exception e) {
            System.out.println("Gagal login.");
            e.printStackTrace();
        }

        return user;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users ORDER BY id_user ASC";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (Exception e) {
            System.out.println("Gagal mengambil data user.");
            e.printStackTrace();
        }

        return users;
    }

    public boolean insertUser(User user) {
        String sql = "INSERT INTO users (username, password, nama_lengkap, role) VALUES (?, ?, ?, ?)";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNamaLengkap());
            ps.setString(4, user.getRole());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Gagal menambahkan user.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE users SET username = ?, password = ?, nama_lengkap = ?, role = ? WHERE id_user = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNamaLengkap());
            ps.setString(4, user.getRole());
            ps.setInt(5, user.getIdUser());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Gagal mengubah user.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int idUser) {
        String sql = "DELETE FROM users WHERE id_user = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idUser);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Gagal menghapus user.");
            e.printStackTrace();
            return false;
        }
    }

    /*
     * METHOD CADANGAN
     * Supaya kode lama Felda tetap tidak error.
     */

    public boolean tambahUser(User user) {
        return insertUser(user);
    }

    public boolean editUser(String usernameLama, User user) {
        User userLama = getUserByUsername(usernameLama);

        if (userLama == null) {
            return false;
        }

        user.setIdUser(userLama.getIdUser());
        return updateUser(user);
    }

    public boolean hapusUser(String username) {
        User user = getUserByUsername(username);

        if (user == null) {
            return false;
        }

        return deleteUser(user.getIdUser());
    }

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (Exception e) {
            System.out.println("Gagal mengambil user berdasarkan username.");
            e.printStackTrace();
        }

        return null;
    }

    private User mapResultSetToUser(ResultSet rs) throws Exception {
        User user = new User();

        user.setIdUser(rs.getInt("id_user"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setNamaLengkap(rs.getString("nama_lengkap"));
        user.setRole(rs.getString("role"));

        return user;
    }
}