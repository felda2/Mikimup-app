package main.service;

import main.dao.UserDAO;
import main.model.User;

public class AuthService {
    private UserDAO userDAO = new UserDAO();

    // Mengambil data user untuk login
    public User loginUser(String username, String password) {
        return userDAO.login(username, password);
    }

    // Melakukan proses registrasi
    public boolean registerUser(User user) {
        return userDAO.register(user);
    }
}